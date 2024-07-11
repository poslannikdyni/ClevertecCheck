package ru.clevertec.check.api.service;

import ru.clevertec.check.to.CheckTO;
import ru.clevertec.check.to.OperationRequestTO;

public interface ApiCheckService {
    CheckTO execute(OperationRequestTO info);
}
