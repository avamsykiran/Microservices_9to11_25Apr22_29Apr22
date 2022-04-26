package in.bta.txns.services;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import in.bta.txns.entities.AccountHolder;
import in.bta.txns.entities.Txn;
import in.bta.txns.entities.TxnType;
import in.bta.txns.exceptions.AccountHolderException;
import in.bta.txns.exceptions.TxnException;
import in.bta.txns.repos.AccountHolderRepo;
import in.bta.txns.repos.TxnRepo;

@Service
public class TxnServiceImpl implements TxnService{

	@Autowired
	private ProfilesClient profiles;
	
	@Autowired
	private AccountHolderRepo ahRepo;
	
	@Autowired
	private TxnRepo txnRepo;
	
	@Override
	public List<Txn> getPeriodicTxnsByAhId(Long ahId,LocalDate start,LocalDate end) throws AccountHolderException {
		return txnRepo.getPeriodicTxnByAhId(ahId, start, end);
	}

	@Override
	public Txn getById(Long txnId) {
		return txnRepo.findById(txnId).orElse(null);
	}

	@Override
	public Txn add(Txn txn) throws AccountHolderException, TxnException {
		if(txn.getHolder()==null)
			throw new TxnException("Txn can not be added to a non mentioned account holder");
		
		long ahid = txn.getHolder().getAhId();
		
		AccountHolder ah = ahRepo.findById(ahid).orElse(null);
		
		if(ah==null) {
			ResponseEntity<Boolean> resp = profiles.checkAccountHolderExists(ahid);
			
			if(resp.getStatusCode()==HttpStatus.OK) {
				if(resp.getBody()==true) {
					ah = new AccountHolder(ahid, 0.0, new TreeSet<>());
				}else {
					throw new TxnException("Txn can not be added to a non existing account holder");		
				}
			}else {
				throw new AccountHolderException("Unable to confirm if such account holder exists");
			}
		}
		
		double cb = ah.getCurrentBalance();
		ah.setCurrentBalance(txn.getType()==TxnType.CREDIT?cb+txn.getAmount():cb-txn.getAmount());
		ah.getTxns().add(txn);
		txn.setHolder(ah);
		ahRepo.save(ah);
		
		return txnRepo.save(txn);
	}

	@Override
	public Txn update(Txn txn) throws AccountHolderException, TxnException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long txnId) throws TxnException {
		txnRepo.deleteById(txnId);		
	}

}
