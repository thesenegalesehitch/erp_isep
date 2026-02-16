import 'package:go_router/go_router.dart';
import '../screens/auth/login_screen.dart';
import '../screens/auth/register_screen.dart';
import '../screens/dashboard_screen.dart';
import '../screens/services/services_screen.dart';
import '../screens/messaging/messaging_screen.dart';
import '../screens/calendar/calendar_screen.dart';
import '../screens/bus/bus_screen.dart';
import '../screens/forum/forum_screen.dart';
import '../screens/activities/activity_detail_screen.dart';

class AppRoutes {
  static const String login = '/login';
  static const String register = '/register';
  static const String dashboard = '/dashboard';
  static const String services = '/services';
  static const String messaging = '/messaging';
  static const String calendar = '/calendar';
  static const String bus = '/bus';
  static const String forum = '/forum';
  static const String activityDetail = '/activities/:id';

  static final GoRouter router = GoRouter(
    initialLocation: login,
    routes: [
      GoRoute(
        path: login,
        builder: (context, state) => const LoginScreen(),
      ),
      GoRoute(
        path: register,
        builder: (context, state) => const RegisterScreen(),
      ),
      GoRoute(
        path: dashboard,
        builder: (context, state) => const DashboardScreen(),
      ),
      GoRoute(
        path: services,
        builder: (context, state) => const ServicesScreen(),
      ),
      GoRoute(
        path: messaging,
        builder: (context, state) => const MessagingScreen(),
      ),
      GoRoute(
        path: calendar,
        builder: (context, state) => const CalendarScreen(),
      ),
      GoRoute(
        path: bus,
        builder: (context, state) => const BusScreen(),
      ),
      GoRoute(
        path: forum,
        builder: (context, state) => const ForumScreen(),
      ),
      GoRoute(
        path: activityDetail,
        builder: (context, state) {
          final id = int.parse(state.pathParameters['id'] ?? '0');
          return ActivityDetailScreen(activityId: id);
        },
      ),
    ],
  );
}

