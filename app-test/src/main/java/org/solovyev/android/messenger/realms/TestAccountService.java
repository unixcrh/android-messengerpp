package org.solovyev.android.messenger.realms;

import org.solovyev.android.messenger.chats.ApiChat;
import org.solovyev.android.messenger.chats.Chat;
import org.solovyev.android.messenger.chats.ChatMessage;
import org.solovyev.android.messenger.chats.AccountChatService;
import org.solovyev.android.messenger.entities.Entity;
import org.solovyev.android.messenger.users.AccountUserService;
import org.solovyev.android.messenger.users.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * User: serso
 * Date: 3/4/13
 * Time: 5:15 PM
 */
public class TestAccountService implements AccountUserService, AccountChatService {
	@Nullable
	@Override
	public User getUserById(@Nonnull String realmUserId) {
		return null;
	}

	@Nonnull
	@Override
	public List<User> getUserContacts(@Nonnull String realmUserId) {
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public List<User> checkOnlineUsers(@Nonnull List<User> users) {
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public List<ChatMessage> getChatMessages(@Nonnull String realmUserId) {
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public List<ChatMessage> getNewerChatMessagesForChat(@Nonnull String realmChatId, @Nonnull String realmUserId) {
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public List<ChatMessage> getOlderChatMessagesForChat(@Nonnull String realmChatId, @Nonnull String realmUserId, @Nonnull Integer offset) {
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public List<ApiChat> getUserChats(@Nonnull String realmUserId) {
		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public String sendChatMessage(@Nonnull Chat chat, @Nonnull ChatMessage message) {
		return "test_message_id";
	}

	@Nonnull
	@Override
	public Chat newPrivateChat(@Nonnull Entity realmChat, @Nonnull String realmUserId1, @Nonnull String realmUserId2) {
		throw new UnsupportedOperationException();
	}
}