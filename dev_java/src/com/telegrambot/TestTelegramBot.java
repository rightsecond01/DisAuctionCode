package com.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TestTelegramBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		
		return "testTelegramBot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if(update.hasMessage() && update.getMessage().hasText()) {
			System.out.println(update);
			SendMessage message = new SendMessage()
					.setChatId(update.getMessage().getChatId())
					.setText(update.getMessage().getText());
			try {
				execute(message);
			}catch(TelegramApiException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String getBotToken() {
		
		return "584361562:AAFmS0QRF_9WY5wyZJpPuYAff8OIacdu7mQ";
	}

}
