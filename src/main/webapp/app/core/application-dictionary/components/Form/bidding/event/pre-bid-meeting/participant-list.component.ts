import { Component, Vue, Mixins, Inject } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const baseApiInvitation = 'api/m-bidding-invitations';
const baseApiPreBidMeeting = 'api/m-pre-bid-meetings';
const baseApiPreBidMeetingParticipant = 'api/m-pre-bid-meeting-participants';

const ParticipantListProps = Vue.extend({
  props: {
    biddingId: Number,
    preBidMeeting: Object,
    visible: Boolean
  }
});

@Component
export default class ParticipantList extends Mixins(AccessLevelMixin, ParticipantListProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  index: boolean = true;
  noUpdate: boolean = true;

  // Loading/process indicators.
  loading: boolean = false;
  updatingAttendees: boolean = false;

  attendedParticipants: number[] = [];
  availableVendors: any[] = [];
  participants: any[] = [];

  /**
   * Cache the 
   */
  attendedVendorIds: Set<number> = new Set();
  vendorTypes: any[] = [];

  /**
   * The list of included vendor IDs.
   */
  addedParticipants: number[] = [];

  /**
   * A set of removed vendor IDs.
   */
  removedParticipants: Set<number> = new Set();

  onAdd() {
    this.index = false;
    this.attendedVendorIds = new Set(this.participants.map(attendee => attendee.vendorId));
    this.retrieveVendorTypes();
    this.retrieveAvailableVendors();
  }

  onAttendeeChanged(value: number[], direction: string, movedItems: number[]) {
    this.noUpdate = false;
    
    if (direction === 'left') {
      movedItems.filter(vendorId => this.attendedVendorIds.has(vendorId))
        .forEach(id => this.removedParticipants.add(id));
    }

    // Add only those that are not already included in the attendee list.
    this.addedParticipants = value.filter(vendorId => !this.attendedVendorIds.has(vendorId));
    console.log('addedParticipants:', this.addedParticipants);
    console.log('removedParticipants:', this.removedParticipants);
  }

  onAttendStatusChange(row: any) {
    this.commonService(baseApiPreBidMeetingParticipant)
      .update(row)
      .then(() => this.$message.success('Attendee status has been changed successfully'))
      .catch(err => {
        console.log('Faild to update the attendee status', err);
        this.$message.error('Failed to update the attendee status');
      })
  }

  onCancel() {
    if (this.index) {
      this.$emit('update:visible', false);
    } else {
      this.resetForm();
      this.index = true;
      this.retrieveParticipants();
    }
  }

  onDialogClose() {
    this.resetForm();
    this.$emit('update:visible', false);
  }

  onDialogOpen() {
    this.retrieveParticipants();
  }

  onSave() {
    this.updatingAttendees = true;
    const { adOrganizationId, id: preBidMeetingId } = this.preBidMeeting;
    const data = {
      ...this.preBidMeeting,
      addedAttendees: this.addedParticipants.map(vendorId =>
        ({
          adOrganizationId: adOrganizationId,
          preBidMeetingId: preBidMeetingId,
          vendorId
        })
      ),
      removedAttendees: Array.from(this.removedParticipants)
    };

    this.commonService(`${baseApiPreBidMeeting}/update-attendees`)
      .create(data)
      .then(() => {
        this.$message.success('Attendees has been updated successfully');
        this.onCancel();
      })
      .catch(err => {
        console.log('Failed to update attendees', err);
        this.$message.error('Failed updating the attendees');
      })
      .finally(() => this.updatingAttendees = false);
  }

  printVendorType(type: string) {
    return this.vendorTypes.find(ref => ref.value === type)?.name || type;
  }

  private resetForm() {
    this.noUpdate = true;
    this.attendedParticipants = [];
    this.availableVendors = [];
    this.participants = [];
    this.addedParticipants = [];
    this.removedParticipants.clear();
  }

  private retrieveParticipants() {
    this.loading = true;
    this.commonService(baseApiPreBidMeetingParticipant)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `preBidMeetingId.equals=${this.preBidMeeting.id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['vendorName']
        }
      })
      .then(res => {
        this.participants = res.data;
        this.attendedParticipants = this.participants.map(participant => participant.vendorId);
      })
      .catch(err => {
        console.log('Failed to get the participants', err);
        this.$message.error('Failed getting the participant list');
      })
      .finally(() => this.loading = false);
  }

  private retrieveAvailableVendors() {
    this.loading = true;
    this.commonService(baseApiInvitation)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `invitationStatus.equals=R`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['vendorName']
        }
      })
      .then(res => {
        this.availableVendors = res.data
          .map((item: any) => ({
            key: item.vendorId,
            label: `${item.vendorCode} - ${item.vendorName} (${this.printVendorType(item.vendorType)})`
          }));
      })
      .catch(err => {
        console.log('Failed to get the participants', err);
        this.$message.error('Failed getting the participant list');
      })
      .finally(() => this.loading = false);
  }

  private retrieveVendorTypes() {
    this.commonService(null)
      .retrieveReferenceLists('vendorType')
      .then(res => this.vendorTypes = res);
  }

}