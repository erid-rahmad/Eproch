import { ADColumnType } from '@/shared/model/ad-column.model';
import { Component, Mixins } from 'vue-property-decorator';
import ContextVariableAccessor from '../components/ContextVariableAccessor';
import { isEmpty } from 'lodash';

@Component
export default class CalloutMixin extends Mixins(ContextVariableAccessor) {

  protected tabName: string = null;

  protected async executeCallout(field: any, callback: (value: string, targetField: string, type?: string) => void) {
    const callouts = field.adCallouts;
    
    for (const callout of callouts) {
      let processResult: any;
      
      if (callout.type === 'PROCESS') {
        const trigger = callout.trigger;
        const parameters = this.buildParameters(trigger.adTriggerParams);
        processResult = await this.runTask(trigger.value, parameters);
      }

      callout.adCalloutTargets.forEach(async (target: any) => {
        if (callout.type === 'PROCESS' && processResult && ! isEmpty(processResult)) {
          callback(processResult[target.sourceField], target.targetName, callout.type);
        } else if (callout.type === 'RESET') {
          callback(target.sourceField, target.targetName, callout.type);
        }
      });
    }
  }

  private buildParameters(triggerParams: any[]) {
    const parameters = {};
    triggerParams.forEach((param: any) => {
      let defaultValue: any = this.getContext(param.defaultValue, this.tabName);

      if (! defaultValue) {
        if (this.isDateField(param.type) || this.isDateTimeField(param.type)) {
          defaultValue = new Date(Date.now());
        } else if (this.isBooleanField(param.type)) {
          defaultValue = false;
        } else if (this.isNumericField(param.type)) {
          defaultValue = 0;
        } else if (this.isStringField(param.type)) {
          defaultValue = '';
        }
      }
      parameters[param.value] = defaultValue;
    });

    return parameters;
  }

  private runTask(serviceName: string, parameters: object) {
    return new Promise<any>(resolve => {
      this.dynamicWindowService(`/api/ad-triggers/process/${serviceName}`)
        .create(parameters)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          resolve(null);
        });
    });
  }

  public isStringField(type: any) {
    return type === ADColumnType.STRING;
  }

  public isNumericField(type: any) {
    return (
      type === ADColumnType.BIG_DECIMAL || type === ADColumnType.DOUBLE ||
      type === ADColumnType.FLOAT || type === ADColumnType.INTEGER ||
      type === ADColumnType.LONG
    );
  }

  public isDateField(type: any) {
    return type === ADColumnType.LOCAL_DATE || type === ADColumnType.ZONED_DATE_TIME;
  }

  public isDateTimeField(type: any) {
    return type === ADColumnType.INSTANT;
  }

  public isBooleanField(type: any) {
    return type === ADColumnType.BOOLEAN;
  }
}