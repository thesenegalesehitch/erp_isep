import { create } from 'zustand';

const useNotificationStore = create((set) => ({
  notifications: [],
  unreadCount: 0,

  setNotifications: (notifications) => set({ 
    notifications,
    unreadCount: notifications.filter(n => !n.read).length 
  }),

  addNotification: (notification) => set((state) => {
    const newNotifications = [notification, ...state.notifications];
    return {
      notifications: newNotifications,
      unreadCount: newNotifications.filter(n => !n.read).length
    };
  }),

  markAsRead: (notificationId) => set((state) => {
    const newNotifications = state.notifications.map(n => 
      n.id === notificationId ? { ...n, read: true } : n
    );
    return {
      notifications: newNotifications,
      unreadCount: newNotifications.filter(n => !n.read).length
    };
  }),
  
  markAllAsRead: () => set((state) => {
    const newNotifications = state.notifications.map(n => ({ ...n, read: true }));
    return {
      notifications: newNotifications,
      unreadCount: 0
    };
  }),
}));

export default useNotificationStore;
