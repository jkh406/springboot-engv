package com.anbtech.webffice.com.config;

import java.util.Collections;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @ClassName : WebfficeConfigAppTransaction.java
 * @Description : Transaction 설정
 *
 * @since  : 2023. 04. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 * </pre>
 *
 */
@Configuration
public class WebfficeConfigAppTransaction {

	@Autowired
	DataSource dataSource;

	@Bean
	public DataSourceTransactionManager txManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}

	// -------------------------------------------------------------
	// TransactionAdvice 설정
	// -------------------------------------------------------------

	@Bean
	public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager) {
		TransactionInterceptor txAdvice = new TransactionInterceptor();
		txAdvice.setTransactionManager(txManager);
		txAdvice.setTransactionAttributeSource(getNameMatchTransactionAttributeSource());
		return txAdvice;
	}

	private NameMatchTransactionAttributeSource getNameMatchTransactionAttributeSource() {
		NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
		txAttributeSource.setNameMap(getRuleBasedTxAttributeMap());
		return txAttributeSource;
	}

	private HashMap<String, TransactionAttribute> getRuleBasedTxAttributeMap() {
		HashMap<String, TransactionAttribute> txMethods = new HashMap<String, TransactionAttribute>();

		RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
		txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		txMethods.put("*", txAttribute);

		return txMethods;
	}

	// -------------------------------------------------------------
	// TransactionAdvisor 설정
	// -------------------------------------------------------------

	@Bean
	public Advisor txAdvisor(DataSourceTransactionManager txManager) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(
			"execution(* com.anbtech.webffice..impl.*Impl.*(..)) or execution(* com.anbtech.webffice.com..*Impl.*(..))");
		return new DefaultPointcutAdvisor(pointcut, txAdvice(txManager));
	}
}
