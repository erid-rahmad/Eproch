package com.bhp.opusb.workflow.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.workflow.AbstractDocActionHandler;
import com.bhp.opusb.workflow.WorkflowDoc;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GenericDocHandler extends AbstractDocActionHandler {
    private ApplicationContext appContext;

    public GenericDocHandler(ApplicationContext ctx){
        this.appContext = ctx;
    }

    private String getBeanName(String tableName, String suffix) {
        final String separator = "_";
        String titleCase = Arrays.stream(tableName.split(separator)).map(
                word -> word.isEmpty() ? word : Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(separator)).replaceAll(separator, "");

        String serviceName = Character.toLowerCase(titleCase.charAt(0)) + titleCase.substring(1) + suffix;
        return serviceName;
    }

    @Override
    public boolean acceptTableName(String tableName) {
        return !"m_bidding".equals(tableName);
    }

    @Override
    protected WorkflowDoc fromId(Long id, String tableName) {
        Object bean = appContext.getBean(getBeanName(tableName, "Service"));
        try {
            Method method = bean.getClass().getMethod("findOne", Long.class);
            Optional<Object> dto = (Optional<Object>) method.invoke(bean, id);

            Method conv = bean.getClass().getMethod("toEntity", dto.get().getClass());
            Object entity = conv.invoke(bean, dto.get());
            return (WorkflowDoc) entity;
        } catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected WorkflowDoc toEntity(Object dto) {
        String qualifiedClassName = dto.getClass().getSimpleName();
        String forBeanName = qualifiedClassName.substring(0,1).toLowerCase()+qualifiedClassName.substring(1, qualifiedClassName.length()-3);
        Object mapper = appContext.getBean(forBeanName+"Service");
        try {
            Method method = mapper.getClass().getMethod("toEntity", dto.getClass());
            Object entity = method.invoke(mapper, dto);
            return (WorkflowDoc) entity;
        } catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Object toDto(WorkflowDoc entity) {
        String qualifiedClassName = entity.getClass().getSimpleName();
        String forBeanName = qualifiedClassName.substring(0,1).toLowerCase()+qualifiedClassName.substring(1, qualifiedClassName.length());
        Object mapper = appContext.getBean(forBeanName+"Service");
        try {
            Method method = mapper.getClass().getMethod("toDto", entity.getClass());
            Object dto = method.invoke(mapper, entity);
            return dto;
        } catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected WorkflowDoc save(WorkflowDoc entity) {
        String qualifiedClassName = entity.getClass().getSimpleName();
        String forBeanName = qualifiedClassName.substring(0,1).toLowerCase()+qualifiedClassName.substring(1, qualifiedClassName.length());
        Object bean = appContext.getBean(forBeanName+"Service");
        try {
            Method conv = bean.getClass().getMethod("toDto", entity.getClass());
            Object dto = conv.invoke(bean, entity);

            Method method = bean.getClass().getMethod("save", dto.getClass());
            Object persist = method.invoke(bean, dto);

            conv = bean.getClass().getMethod("toEntity", persist.getClass());
            Object persistedDto = conv.invoke(bean, persist);
            return (WorkflowDoc) persistedDto;
        } catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
    }
}
