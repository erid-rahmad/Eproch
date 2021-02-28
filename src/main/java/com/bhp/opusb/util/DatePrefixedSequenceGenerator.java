package com.bhp.opusb.util;

import java.io.Serializable;
import java.util.Properties;

import com.bhp.opusb.domain.AbstractTransactionalEntity;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class DatePrefixedSequenceGenerator extends SequenceStyleGenerator {

  public static final String DATE_FORMAT_PARAMETER = "dateFormat";
  public static final String DATE_FORMAT_DEFAULT = "%tY-%tm";
    
  public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
  public static final String NUMBER_FORMAT_DEFAULT = "%04d";
    
  public static final String DATE_NUMBER_SEPARATOR_PARAMETER = "dateNumberSeparator";
  public static final String DATE_NUMBER_SEPARATOR_DEFAULT = "";
    
  private String format;

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
    super.configure(LongType.INSTANCE, params, serviceRegistry);

    String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAMETER, params, DATE_FORMAT_DEFAULT).replace("%", "%1"); 
    String numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT).replace("%", "%2"); 
    String dateNumberSeparator = ConfigurationHelper.getString(DATE_NUMBER_SEPARATOR_PARAMETER, params, DATE_NUMBER_SEPARATOR_DEFAULT); 
    this.format = dateFormat + dateNumberSeparator + numberFormat;
  }

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    AbstractTransactionalEntity<?> entity = (AbstractTransactionalEntity<?>) object;
    return String.format(format, entity.getDateTrx(), super.generate(session, object));
  }
  
}
