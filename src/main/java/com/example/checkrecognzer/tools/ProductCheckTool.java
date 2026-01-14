package com.example.checkrecognzer.tools;

import com.example.checkrecognzer.models.ProductCheck;
import com.example.checkrecognzer.services.ProductCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductCheckTool {
    private final ProductCheckService productCheckService;

    @Tool(name = "getAllChecks", description = "Получить все чеки из базы данных")
    public List<ProductCheck> getAllChecks() {
        return productCheckService.getAllChecksWithItems();
    }

    @Tool(name = "findById", description = "Получить чек из базы данных по идентификатору")
    public ProductCheck findById(@ToolParam(description = "идентификатор чека") Long id) {
        return productCheckService.findById(id);
    }
}
