package com.intuite.shopped.service;

import com.intuite.shopped.domain.Transaction;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.TransactionRepository;
import com.intuite.shopped.service.dto.TransactionDTO;
import com.intuite.shopped.service.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionDTO save(TransactionDTO transactionDTO) {
        log.debug("Request to save Transaction : {}", transactionDTO);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable)
            .map(transactionMapper::toDto);
    }


    /**
     * Get one transaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id)
            .map(transactionMapper::toDto);
    }

    /**
     * Delete the transaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }

    public void sendPayment(TransactionDTO transactionDTO, User isUser) {
//        Payout payout = new Payout();
//
//        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();
//        senderBatchHeader.setEmailSubject("PayPal Shopped Integration");
//
//        Currency amnt = new Currency();
//        // Transaction of 1 unit with US Dollars as unit.
//        amnt.setValue("20").setCurrency("USD");
//
//        logger.info("Payout started {} ",amnt);
//
//        List<PayoutItem> items = insertRecipient("nicolas", amnt);
//
//        logger.info("Added {} to payments",items);
//
//        payout.setSenderBatchHeader(senderBatchHeader).setItems(items);
//
//        //paypalMode can be either "sandbox" or "live"
//        APIContext apiContext = new APIContext(
//            clientId, clientSecret, mode);
//
//        logger.info("Initializing api call {}",payout);
//
//        PayoutBatch batch = payout.createSynchronous(apiContext);
//        String batchId = batch.getBatchHeader().getPayoutBatchId();
//
//        logger.info("Payout finished");
//
//        return email;

        logger.info("Payout for {} to {}",transactionDTO.getAmount(),isUser.getEmail());
    }
}
