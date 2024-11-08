package com.zerobase.service.customer;

import com.zerobase.domain.customer.ChangeBalanceForm;
import com.zerobase.domain.model.CustomerBalanceHistory;
import com.zerobase.domain.repository.CustomerBalanceHistoryRepository;
import com.zerobase.domain.repository.CustomerRepository;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

    private final CustomerRepository customerRepository;
    private final CustomerBalanceHistoryRepository
            customerBalanceHistoryRepository;

    // 해당 예외가 발생하면 롤백하지 않는다.
    @Transactional(noRollbackFor = {CustomException.class})
    public CustomerBalanceHistory changeBalance(
            Long customerId, ChangeBalanceForm form) throws CustomException {

        CustomerBalanceHistory customerBalanceHistory =
                customerBalanceHistoryRepository
                        .findFirstByCustomer_IdOrderByIdDesc(customerId)
                        .orElse(CustomerBalanceHistory.builder()
                                .changeMoney(0)
                                .currentMoney(0)
                                .customer(customerRepository
                                        .findById(customerId)
                                        .orElseThrow(() -> new CustomException(
                                                ErrorCode.NOT_FOUND_USER)
                                        )
                                )
                                .build());

        if (customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
        }

        customerBalanceHistory = CustomerBalanceHistory.builder()
                .changeMoney(form.getMoney())
                .currentMoney(
                        customerBalanceHistory.getCurrentMoney() +
                                form.getMoney()
                )
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .customer(customerBalanceHistory.getCustomer())
                .build();

        customerBalanceHistory.getCustomer()
                .setBalance(customerBalanceHistory.getCurrentMoney());

        return customerBalanceHistory;
    }
}
