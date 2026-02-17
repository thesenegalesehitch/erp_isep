import { create } from 'zustand';

const useMessageStore = create((set) => ({
  conversations: [],
  currentConversation: null,
  messages: {}, // Map conversationId to messages array

  setConversations: (conversations) => set({ conversations }),
  
  setCurrentConversation: (conversation) => set({ currentConversation: conversation }),
  
  addMessage: (conversationId, message) => set((state) => {
    const currentMessages = state.messages[conversationId] || [];
    return {
      messages: {
        ...state.messages,
        [conversationId]: [...currentMessages, message]
      }
    };
  }),

  setMessages: (conversationId, messages) => set((state) => ({
    messages: {
      ...state.messages,
      [conversationId]: messages
    }
  })),
}));

export default useMessageStore;
