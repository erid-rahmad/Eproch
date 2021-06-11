import { IADField } from '@/shared/model/ad-field.model';
import { hasPrecision, hasReferenceList, isActiveStatusField, isAddressField, isAttachmentField, isBooleanField, isCheckboxGroup, isDateField, isDateTimeField, isMultiValues, isNewRecord, isNumericField, isPasswordField, isRichText, isStringField, isTableDirectLink } from '@/utils/validate';
import { Component, Vue } from 'vue-property-decorator';

@Component({
  methods: {
    isNewRecord,
    isStringField,
    isPasswordField,
    isNumericField,
    isDateField,
    isDateTimeField,
    isBooleanField,
    hasPrecision,
    hasReferenceList,
    isRichText,
    isTableDirectLink,
    isAddressField,
    isAttachmentField,
    isCheckboxGroup,
    isMultiValues,
    isActivatorSwitch: isActiveStatusField
  }
})
export default class FieldTypeValidationMixin extends Vue {

  protected isNewRecord!: (row: any) => boolean;
  protected isStringField!: (field: IADField) => boolean;
  protected isPasswordField!: (field: IADField) => boolean;
  protected isNumericField!: (field: IADField) => boolean;
  protected isDateField!: (field: IADField) => boolean;
  protected isDateTimeField!: (field: IADField) => boolean;
  protected isBooleanField!: (field: IADField) => boolean;
  protected isActivatorSwitch!: (field: IADField) => boolean;
  protected hasPrecision!: (field: IADField) => boolean;
  protected hasReferenceList!: (field: IADField) => boolean;
  protected isTableDirectLink!: (field: IADField) => boolean;
  protected isAddressField!: (field: IADField) => boolean;
  protected isAttachmentField!: (field: IADField) => boolean;
  protected isRichText!: (field: IADField) => boolean;
  protected isCheckboxGroup!: (field: IADField) => boolean;
  protected isMultiValues!: (field: IADField) => boolean;

}