package com.example.checkrecognzer.services;

import com.example.checkrecognzer.models.ProductCheck;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.InputStream;

@Service
public class CheckRecognizerService {
    private final ChatClient chatClient;

    public CheckRecognizerService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public ProductCheck getCheckInfo(InputStream imageInputStream, String contentType) {
        return chatClient.prompt()
                .system(systemMessage -> systemMessage
                        .text("""
            Извлеки данные из чека в JSON такого формата:
            {
              "storeName": "название магазина из чека (ИП или название)",
              "totalPrice": итоговая сумма к оплате в рублях,
              "discount": общая скидка на чек в рублях (0 если нет),
              "productItems": [
                {
                  "name": "название товара",
                  "quantity": количество в кг/шт ,
                  "pricePerUnit": цена за кг/ед в рублях,
                  "totalPrice": стоимость позиции в рублях,
                  "discount": скидка на товар в рублях (0 если нет)
                }
              ]
            }
            
            ПРАВИЛА для российских чеков:
            1. storeName: бери из ИП/названия магазина (например "ИП Смирнов Антон Иванович")
            2. totalPrice: это "ИТОГ" в рублях
            3. discount: скидка на весь чек
            4. Для товаров:
               - quantity: бери десятичное число из "Кол-во" (0,200 кг)
               - pricePerUnit: Цена за единицу товара в рублях
               - totalPrice: стоимость в рублях
               - discount: 0 (если нет акции на конкретный товар)
            5. Игнорируй: НДС, оплату, сдачу, ФП данные, коды, ссылки
            6. Если поле не найдено - null или 0
            Верни ТОЛЬКО JSON, без пояснений.
            """)
                )
                .user(userMessage -> userMessage
                        .media(MimeTypeUtils.parseMimeType(contentType), new InputStreamResource(imageInputStream))
                        .text("Извлеки данные чека в соответствии с инструкциями системы.")
                )
                .call()
                .entity(ProductCheck.class);
    }
}