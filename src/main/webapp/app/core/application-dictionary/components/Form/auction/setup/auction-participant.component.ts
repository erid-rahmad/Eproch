import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import Schema from 'async-validator';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';

const baseApiAuctionParticipant = 'api/m-auction-participants';
const baseApiVendor = 'api/c-vendors';
const baseApiUser = 'api/ad-users';

const AuctionParticipantProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class AuctionParticipant extends Mixins(AccessLevelMixin, AuctionParticipantProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  gutterSize: number = 24;

  loadingParticipants: boolean = false;
  newRecord: boolean = false;
  deleteConfirmationVisible: boolean = false;

  auction: any = {};
  participants: any[] = [];
  tmpParticipant: any = {};

  participantValidationSchema = {
    vendorId: {
      required: true,
      message: 'Vendor is required'
    },
    userUserId: {
      required: true,
      message: 'User is required'
    }
  };
  
  vendorOptions: any[] = [];
  userOptions: any[] = [];

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.auction.documentStatus && this.auction.documentStatus !== 'DRF';
  }

  onAddClicked() {
    this.retrieveVendors();

    this.participants.splice(0, 0, {
      editing: true,
      adOrganizationId: AccountStoreModule.organizationInfo.id,
      auctionId: this.auction.id,
      vendorId: null,
      userUserId: null,
      email: null
    });

    this.newRecord = true;
  }

  onDeleteClicked(row: any) {
    this.tmpParticipant = row;
    this.deleteConfirmationVisible = true;
  }

  onEditCanceled(index: number) {
    if (this.newRecord) {
      this.participants.splice(0, 1);
      this.newRecord = false;
    } else {
      this.participants.splice(index, 1, this.tmpParticipant);
      this.tmpParticipant = {};
    }
  }

  onEditClicked(row: any) {
    this.retrieveVendors(row.vendorId);
    this.retrieveVendorUsers(row.vendorId, row.userUserId);
    this.tmpParticipant = {...row};
    row.editing = true;
  }

  onSaveClicked(row: any) {
    const validator = new Schema(this.participantValidationSchema);
    validator.validate(row, (errors: any[]) => {
      if (errors) {
        for (const error of errors) {
          row[`${error.field}Error`] = true;
          row[`${error.field}Message`] = error.message;
        }
        this.$message.error(errors[0].message);
      } else {
        row.vendorIdError = false;
        row.vendorIdMessage = null;
        row.userUserIdError = false;
        row.userUserIdMessage = null;
        this.saveParticipant(row)
      }
    })
  }

  onUserChanged(row: any, userId: number) {
    row.email = this.userOptions.find(user => user.id === userId)?.email;
  }

  onVendorChanged(row: any, vendorId: number) {
    row.userUserId = null;
    row.email = null;
    this.retrieveVendorUsers(vendorId);
  }

  created() {
    this.auction = {...this.data};
    this.retrieveParticipants(this.data.id);
  }

  deleteRecord() {
    this.commonService(baseApiAuctionParticipant)
      .delete(this.tmpParticipant.id)
      .then(() => {
        this.$message.success('Participant has been deleted successfully');
        this.retrieveParticipants(this.data.id);
        this.tmpParticipant = {};
      })
      .catch(err => {
        console.error('Failed to delete participant', err);
        this.$message.error('Failed to delete participant');
      })
      .finally(() => this.deleteConfirmationVisible = false)
  }

  printEmail(userId: number) {
    return this.userOptions.find(user => user.id === userId)?.email;
  }

  private retrieveParticipants(auctionId: number) {
    this.loadingParticipants = true;
    this.commonService(baseApiAuctionParticipant)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => {
        this.participants = res.data
          .map(item => {
            item.editing = false;
            return item;
          });
      })
      .finally(() => this.loadingParticipants = false);
  }

  retrieveVendors(query?: string | number) {
    let baseQuery = [
      'active.equals=true',
      'documentStatus.equals=APV'
    ];

    if (!!query) {
      baseQuery = [
        ...baseQuery,
        ...[ typeof query === 'string' ? `name.contains=${query}` : `id.equals=${query}` ]
      ];
    }

    this.commonService(baseApiVendor)
      .retrieve({
        criteriaQuery: this.updateCriteria(baseQuery),
        paginationQuery: {
          page: 0,
          size: 20,
          sort: ['name']
        }
      })
      .then(res => this.vendorOptions = res.data);
  }

  retrieveVendorUsers(vendorId: number, query?: string | number) {
    let baseQuery = [
      'active.equals=true',
      'vendor.equals=true',
      `cVendorId.equals=${vendorId}`
    ];

    if (!!query) {
      baseQuery = [
        ...baseQuery,
        ...[ typeof query === 'string' ? `userLogin.contains=${query}` : `id.equals=${query}` ]
      ];
    }

    this.commonService(baseApiUser)
      .retrieve({
        criteriaQuery: this.updateCriteria(baseQuery),
        paginationQuery: {
          page: 0,
          size: 20,
          sort: ['userLogin']
        }
      })
      .then(res => this.userOptions = res.data);
  }

  public save() {
    console.log('Saving auction participants...');
  }

  private saveParticipant(data: any) {
    const newRecord = !data.id;

    this.loadingParticipants = true;
    this.commonService(baseApiAuctionParticipant)
      [newRecord ? 'create' : 'update'](data)
      .then(res => {
        this.$message.success(`Participant has been ${newRecord ? 'added' : 'updated'} successfully`);
        this.newRecord = false;
        this.tmpParticipant = {};
        this.retrieveParticipants(this.auction.id);
      })
      .catch(err => {
        console.error('Failed to save the item', err);
        this.$message.error('Failed to save the item');
        this.loadingParticipants = false;
      });
  }
}