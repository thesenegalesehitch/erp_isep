import { create } from 'zustand'

const useAuthStore = create((set) => ({
  user: JSON.parse(localStorage.getItem('user') || 'null'),
  token: localStorage.getItem('accessToken') || null,
  isAuthenticated: !!localStorage.getItem('accessToken'),
  
  setUser: (user) => {
    localStorage.setItem('user', JSON.stringify(user))
    set({ user, isAuthenticated: true })
  },
  
  setToken: (token) => {
    localStorage.setItem('accessToken', token)
    set({ token, isAuthenticated: true })
  },
  
  logout: () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('user')
    set({ user: null, token: null, isAuthenticated: false })
  }
}))

export default useAuthStore

