import { Component, Mixins, Vue, Watch, Inject } from "vue-property-decorator";
import AccessLevelMixin from '../../mixins/AccessLevelMixin';
import { ElDropdown } from 'element-ui/types/dropdown';
import { ADWindowType } from '@/shared/model/ad-window.model';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

const DocumentActionButtonProps = Vue.extend({
  props: {
    /**
     * ADWindowType
     */
    windowType: String,

    approved: Boolean,

    documentTypeId: Number,
    nextAction: String,
    
    size: {
      type: String,
      default: () => {
        return 'small'
      }
    }
  }
});

@Component
export default class DocumentActionButton extends Mixins(AccessLevelMixin, DocumentActionButtonProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  actions: any[] = [];

  get defaultDocumentAction() {
    if (this.approved) {
      return 'Approved';
    }
    const docAction = this.actions.find(action => action.value === this.nextAction);
    return docAction?.name || this.nextAction;
  }

  get hasActions() {
    return this.transactionWindow && this.actions.length;
  }

  get transactionWindow() {
    return this.windowType === ADWindowType.TRANSACTION;
  }

  @Watch('approved')
  onApprovedChange(approved: boolean) {
    this.$nextTick(() => {
      (<ElDropdown>this.$refs.mainButton)?.$el
        ?.querySelectorAll('button')
        .forEach(button => {
          button.disabled = approved;
          button.classList[approved ? 'add' : 'remove']('is-disabled');
        });
    });
  }

  @Watch('documentTypeId')
  onDocumentTypeIdChange(id?: number) {
    console.log('doc type id changed', id);
    if (id && this.windowType === ADWindowType.TRANSACTION) {
      this.retrieveDocumentActions(id);
    }
  }

  created() {
    this.onDocumentTypeIdChange(this.documentTypeId);
  }

  public applyDocumentAction(action: object) {
    this.$emit('change', action);
  }

  public applyNextDocumentAction() {
    const docAction = this.actions.find(action => action.value === this.nextAction);

    if (docAction !== void 0) {
      this.applyDocumentAction(docAction);
    }
  }

  private retrieveDocumentActions(docTypeId: number) {
    if (accountStore.grantedDocActions.has(docTypeId)) {
      const referenceListIds = [...accountStore.grantedDocActions.get(docTypeId)];

      this.commonService('/api/ad-reference-lists')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          ...referenceListIds.map(id => `id.in=${id}`)
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['name']
        }
      })
      .then(res => {
        this.actions = res.data;
      })
      .catch(err => {
        console.log('Failed to get document actions. ', err);
        this.$message.warning('Failed to get document actions');
      });
    }
  }
}