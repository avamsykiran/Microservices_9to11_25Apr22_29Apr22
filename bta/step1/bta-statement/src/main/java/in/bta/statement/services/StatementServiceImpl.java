package in.bta.statement.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import in.bta.statement.models.Statement;

@Service
public class StatementServiceImpl implements StatementService {

	@Override
	public Statement getStatement(long ahid, LocalDate start, LocalDate end) {
		return null;
	}

}
