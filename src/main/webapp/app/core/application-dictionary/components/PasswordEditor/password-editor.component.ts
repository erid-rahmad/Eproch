import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject } from 'vue-property-decorator';

const PasswordEditorProps = Vue.extend({
  props: {
    disabled: Boolean,
    userId: Number
  }
})

@Component
export default class PasswordEditor extends PasswordEditorProps {

  @Inject('dynamicWindowService')
  private service: (baseApiUrl: string) => DynamicWindowService;

  private baseApiUrl: string = '/api/account/change-password';

  // Used in Vue template.
  loading: boolean = false;
  validationSchema = {};
  columnSpacing = 24;
  account: Record<string, any> = {
    newPassword: null
  };
  visible: boolean = false;

  onSave() {
    (<ElForm>this.$refs.form).validate(passed => {
      if (passed) {
        this.loading = true;
        this.service(`${this.baseApiUrl}/${this.userId}`)
          .update(this.account)
          .then(res => {
            this.$message({
              message: `Password has been updated successfully`
            });
            this.visible = false;
          }).catch(error => {
            console.log('Error updating password', error);
            this.$message('Failed updating the password');
          }).finally(() => {
            this.loading = false;
          });
      }
    });
  }

}