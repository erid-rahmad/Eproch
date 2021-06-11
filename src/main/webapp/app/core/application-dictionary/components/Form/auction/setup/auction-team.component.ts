import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { ElForm } from 'element-ui/types/form';
import { ElSelect } from 'element-ui/types/select';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiAuctionTeam = 'api/m-auction-teams';
const baseApiUser = 'api/ad-users';

const AuctionTeamProps = Vue.extend({
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
export default class AuctionTeam extends Mixins(AccessLevelMixin, AuctionTeamProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingTeams: boolean = false;
  deleteConfirmationVisible: boolean = false;
  teamMemberFormVisible: boolean = false;

  teams: any[] = [];
  roleOptions: any[] = [];
  userOptions: any[] = [];

  auction: any = {};
  teamMember: any = {};

  teamMemberValidationSchema = {
    role: {
      required: true,
      message: 'Role is required'
    },
    userUserId: {
      required: true,
      message: 'User is required'
    }
  };

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.auction.documentStatus && this.auction.documentStatus !== 'DRF';
  }

  onDeleteClicked(row: any) {
    this.teamMember = {...row};
    this.deleteConfirmationVisible = true;
  }

  onEditClicked(row: any) {
    this.teamMember = {...row};
    this.teamMemberFormVisible = true;
  }

  onTeamMemberFormClose() {
    this.resetTeamMemberForm();
  }

  onTeamMemberFormOpen() {
    this.retrieveUserOptions();
  }

  onTeamMemberFormOpened() {
    this.$nextTick(() => {
      (<ElSelect>this.$refs.memberRole).focus();
    });
  }

  created() {
    this.auction = {...this.data};
    this.retrieveRoleOptions();
    this.retrieveTeams(this.data.id);
  }

  deleteRecord() {
    this.commonService(baseApiAuctionTeam)
      .delete(this.teamMember.id)
      .then(() => {
        this.$message.success('Member has been deleted successfully');
        this.retrieveTeams(this.data.id);
      })
      .catch(err => {
        console.error('Failed to delete team member', err);
        this.$message.error('Failed to delete team member');
      })
      .finally(() => this.deleteConfirmationVisible = false)
  }

  printRoleName(value: string) {
    return this.roleOptions.find(role => role.value === value)?.name || value;
  }

  private retrieveTeams(auctionId: number) {
    this.loadingTeams = true;
    this.commonService(baseApiAuctionTeam)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 50,
          sort: ['id']
        }
      })
      .then(res => this.teams = res.data)
      .finally(() => this.loadingTeams = false);
  }

  private resetTeamMemberForm() {
    this.teamMember = {
      role: null,
      userUserId: null
    };

    (<ElForm>this.$refs.teamMemberForm).resetFields();
  }

  private retrieveRoleOptions() {
    this.commonService(null)
      .retrieveReferenceLists('auctionTeamRoles')
      .then(ref => this.roleOptions = ref);
  }

  private retrieveUserOptions() {
    this.commonService(baseApiUser)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'employee.equals=true'
        ])
      })
      .then(res => this.userOptions = res.data);
  }

  public save() {
    console.log('Saving auction team...');
  }

  saveMember() {
    (<ElForm>this.$refs.teamMemberForm).validate(passed => {
      if (passed) {
        const newRecord = !this.teamMember.id;

        if (newRecord) {
          this.teamMember.auctionId = this.auction.id;
          this.teamMember.adOrganizationId = AccountStoreModule.organizationInfo.id;
        }

        this.commonService(baseApiAuctionTeam)
          [newRecord ?  'create' : 'update'](this.teamMember)
          .then(res => {
            this.$message.success(`Member has been ${newRecord ? 'created' : 'updated'} successfully`);
            this.teamMemberFormVisible = false;
            this.retrieveTeams(this.data.id);
          })
          .catch(err => {
            console.error('Failed to save team member', err);
            this.$message.error('Failed to save team member');
          });
      }
    });
  }
}