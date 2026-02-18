#!/bin/bash

# ISEP ERP Production Deployment Script
# This script deploys the ISEP ERP application to production

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Logging
log() {
    echo -e "${GREEN}[$(date +'%Y-%m-%d %H:%M:%S')] $1${NC}"
}

warn() {
    echo -e "${YELLOW}[$(date +'%Y-%m-%d %H:%M:%S')] WARNING: $1${NC}"
}

error() {
    echo -e "${RED}[$(date +'%Y-%m-%d %H:%M:%S')] ERROR: $1${NC}"
    exit 1
}

# Check if running as root
check_root() {
    if [[ $EUID -eq 0 ]]; then
        error "This script should not be run as root"
    fi
}

# Check prerequisites
check_prerequisites() {
    log "Checking prerequisites..."
    
    # Check Docker
    if ! command -v docker &> /dev/null; then
        error "Docker is not installed"
    fi
    
    # Check Docker Compose
    if ! command -v docker-compose &> /dev/null; then
        error "Docker Compose is not installed"
    fi
    
    # Check if .env.production exists
    if [[ ! -f ".env.production" ]]; then
        error ".env.production file not found. Please create it first."
    fi
    
    log "Prerequisites check completed"
}

# Create necessary directories
create_directories() {
    log "Creating necessary directories..."
    
    mkdir -p uploads
    mkdir -p logs
    mkdir -p nginx/ssl
    mkdir -p monitoring/grafana/dashboards
    mkdir -p monitoring/grafana/datasources
    
    log "Directories created"
}

# Generate SSL certificates (self-signed for development)
generate_ssl() {
    log "Generating SSL certificates..."
    
    if [[ ! -f "nginx/ssl/cert.pem" ]]; then
        openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
            -keyout nginx/ssl/key.pem \
            -out nginx/ssl/cert.pem \
            -subj "/C=SN/ST=Dakar/L=Dakar/O=ISEP/OU=IT/CN=isep-erp.sn"
        log "SSL certificates generated"
    else
        log "SSL certificates already exist"
    fi
}

# Build and deploy
deploy() {
    log "Starting deployment..."
    
    # Load environment variables
    export $(cat .env.production | grep -v '^#' | xargs)
    
    # Stop existing services
    log "Stopping existing services..."
    docker-compose down
    
    # Build images
    log "Building Docker images..."
    docker-compose build --no-cache
    
    # Start services
    log "Starting services..."
    docker-compose up -d
    
    # Wait for services to be healthy
    log "Waiting for services to be healthy..."
    sleep 30
    
    # Check service health
    check_health
    
    log "Deployment completed successfully"
}

# Check service health
check_health() {
    log "Checking service health..."
    
    # Check backend
    if curl -f http://localhost:8080/actuator/health &> /dev/null; then
        log "Backend is healthy"
    else
        error "Backend is not responding"
    fi
    
    # Check frontend
    if curl -f http://localhost/health &> /dev/null; then
        log "Frontend is healthy"
    else
        error "Frontend is not responding"
    fi
    
    # Check database
    if docker-compose exec -T postgres pg_isready -U isep_erp_user &> /dev/null; then
        log "Database is healthy"
    else
        error "Database is not ready"
    fi
    
    # Check Redis
    if docker-compose exec -T redis redis-cli ping &> /dev/null; then
        log "Redis is healthy"
    else
        error "Redis is not ready"
    fi
}

# Show service status
show_status() {
    log "Service status:"
    docker-compose ps
    
    log "Service URLs:"
    echo "Frontend: https://localhost"
    echo "Backend API: https://localhost/api"
    echo "Grafana: http://localhost:3000 (admin/grafana_secure_admin_2024)"
    echo "Prometheus: http://localhost:9090"
}

# Cleanup function
cleanup() {
    log "Cleaning up..."
    docker-compose down
    docker system prune -f
    log "Cleanup completed"
}

# Main execution
main() {
    log "Starting ISEP ERP deployment..."
    
    check_root
    check_prerequisites
    create_directories
    generate_ssl
    deploy
    show_status
    
    log "Deployment completed successfully!"
    log "Access the application at https://localhost"
    warn "Remember to update your SSL certificates for production use"
}

# Handle script arguments
case "${1:-}" in
    "deploy")
        main
        ;;
    "cleanup")
        cleanup
        ;;
    "status")
        show_status
        ;;
    "health")
        check_health
        ;;
    *)
        echo "Usage: $0 {deploy|cleanup|status|health}"
        echo "  deploy  - Deploy the application"
        echo "  cleanup - Clean up Docker resources"
        echo "  status  - Show service status"
        echo "  health  - Check service health"
        exit 1
        ;;
esac
