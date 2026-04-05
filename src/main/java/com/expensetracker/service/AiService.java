package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.SavingsGoal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create();

    private String callAi(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        Map<String, Object> text = new HashMap<>();
        text.put("text", prompt);

        Map<String, Object> parts = new HashMap<>();
        parts.put("parts", List.of(text));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(parts));

        try {
            Map response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List candidates = (List) response.get("candidates");
            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List responseParts = (List) content.get("parts");
            Map part = (Map) responseParts.get(0);
            return (String) part.get("text");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AI error: " + e.getMessage());
            return "AI unavailable at the moment.";
        }
    }

    public String getMonthlySpendingSummary(Map<String, BigDecimal> expensesByCategory,
                                            BigDecimal totalExpenses,
                                            BigDecimal totalIncome,
                                            int month, int year) {

        StringBuilder prompt = new StringBuilder();
        prompt.append("I am analyzing my finances for month ").append(month)
                .append(" of year ").append(year).append(".\n")
                .append("Total Income: ₹").append(totalIncome).append("\n")
                .append("Total Expenses: ₹").append(totalExpenses).append("\n")
                .append("Expenses by category:\n");

        for (Map.Entry<String, BigDecimal> entry : expensesByCategory.entrySet()) {
            prompt.append("- ").append(entry.getKey()).append(": ₹").append(entry.getValue()).append("\n");
        }

        prompt.append("""
              
              STRICT RULES FOR OUTPUT:
                1. Act as a professional Financial Analyst.
                2. NO conversational filler, greetings, or closing remarks.
                3. Provide a detailed 'Net Surplus/Deficit' calculation.
                4. Provide 4 to 5 DETAILED bullet points highlighting specific spending patterns and areas for improvement.
                5. MANDATORY FORMATTING: You MUST place a double line break after every single bullet point to ensure they are separated.
                6. Do NOT use Markdown tables. Use plain text bullet points (e.g., using a dash "-" or bullet "•").
                7. Provide a thorough analysis (aim for 200 to 250 words).
        """);

        return callAi(prompt.toString());
    }

    public String getBudgetSuggestions(Map<String, BigDecimal> expensesByCategory,
                                       List<Budget> budgets) {

        StringBuilder prompt = new StringBuilder();
        prompt.append("Here is my current spending vs limits:\n");

        for (Map.Entry<String, BigDecimal> entry : expensesByCategory.entrySet()) {
            prompt.append("- ").append(entry.getKey()).append(": spent ₹").append(entry.getValue()).append("\n");
        }

        prompt.append("\nCurrent limits:\n");
        for (Budget budget : budgets) {
            prompt.append("- ").append(budget.getCategory() != null ? budget.getCategory().getName() : "Unknown")
                    .append(": ₹").append(budget.getLimitAmount()).append("\n");
        }

        prompt.append("""             
                
                STRICT RULES FOR OUTPUT:
                    1. Act as a professional Financial Analyst.
                    2. NO conversational filler or greetings.
                    3. List the recommended new budget limits using plain text bullet points.
                    4. MANDATORY FORMATTING: You MUST place a double line break after every single bullet point to ensure they are separated.
                    5. Briefly state the reason for each change on the same line.
                    6. Note any money freed up or deficits created by these changes.
                    7. Provide a thorough analysis (aim for 150 to 200 words).
                """);

        return callAi(prompt.toString());
    }

    public String getSavingsAdvice(List<SavingsGoal> savingsGoals,
                                   BigDecimal totalIncome,
                                   BigDecimal totalExpenses) {

        BigDecimal savings = totalIncome.subtract(totalExpenses);

        StringBuilder prompt = new StringBuilder();
        prompt.append("My monthly income is ₹").append(totalIncome).append("\n")
                .append("My monthly expenses are ₹").append(totalExpenses).append("\n")
                .append("My current monthly savings capacity is ₹").append(savings).append("\n")
                .append("My savings goals are:\n");

        for (SavingsGoal goal : savingsGoals) {
            prompt.append("- ").append(goal.getName())
                    .append(": target ₹").append(goal.getTargetAmount())
                    .append(", saved so far ₹").append(goal.getCurrentAmount())
                    .append(", deadline ").append(goal.getDeadline()).append("\n");
        }

        prompt.append("""                
                
                STRICT RULES FOR OUTPUT:
                    1. Act as a professional Financial Analyst.
                    2. NO conversational filler or greetings.
                    3. Calculate the exact month/year each goal will be hit if the full savings capacity is utilized.
                    4. Provide a 3 to 4 step numbered action plan in plain text.
                    5. MANDATORY FORMATTING: You MUST place a double line break after every single numbered step to ensure they are separated.
                    6. Provide a thorough analysis (aim for 150 to 200 words).
                """);

        return callAi(prompt.toString());
    }
}