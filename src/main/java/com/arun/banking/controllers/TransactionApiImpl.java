package com.arun.banking.controllers;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.validation.Valid;

import com.arun.banking.api.TransactionApi;
import com.arun.banking.entities.AccountBalanceHistory;
import com.arun.banking.model.TransactionDetails;
import com.arun.banking.model.TransactionHistoryRequest;
import com.arun.banking.model.TransactionId;
import com.arun.banking.services.TransactionService;
import com.arun.banking.util.PDFUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller to ensure the Healt of the system
 */
@RestController
public class TransactionApiImpl implements TransactionApi {

    @Autowired
    TransactionService transactionService;

    /**
     * End point to create a transaction
     * 
     * @param transactionDetails Details to create a transaction
     * @return returns transaction id
     */
    @Override
    public ResponseEntity<TransactionId> makeTransaction(@Valid TransactionDetails transactionDetails) {
        TransactionId transactionId = this.transactionService.makeTransaction(transactionDetails);
        return new ResponseEntity<>(transactionId, HttpStatus.OK);
    }

    /**
     * End point to fetch the transactions given a time frame in epoch
     * 
     * @param transactionHistoryRequest request consisting account number from and
     *                                  to time in epoch milliseconds
     * @return a pdf file
     */
    @Override
    public ResponseEntity<Resource> getHistory(@Valid TransactionHistoryRequest transactionHistoryRequest) {
        List<AccountBalanceHistory> abh = this.transactionService.getTransactionHistory(transactionHistoryRequest);

        // Building the PDF
        ByteArrayOutputStream byteStream = (new PDFUtil()).convertTransactionHistoryToPDF(abh);

        return new ResponseEntity<>(new ByteArrayResource(byteStream.toByteArray()), HttpStatus.OK);
    }
}
