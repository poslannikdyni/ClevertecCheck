package main.java.ru.clevertec.check.api.service;

import main.java.ru.clevertec.check.to.CheckTO;
import main.java.ru.clevertec.check.to.OperationRequestTO;

public interface ApiCheckService {
    CheckTO execute(OperationRequestTO info);
}
