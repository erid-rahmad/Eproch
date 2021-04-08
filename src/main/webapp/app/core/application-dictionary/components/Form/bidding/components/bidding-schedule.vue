<template>
  <div class="bidding-schedule">

    <el-row>
      <el-col
        :xs="24"
        :sm="18"
        :xl="16"
      >
        <el-divider content-position="left"><h4>Event Schedule</h4></el-divider>
        <el-table
          v-loading="processing"
          ref="biddingSchedules"
          border
          :data="bidding.biddingSchedules"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          highlight-current-row
          size="mini"
          stripe
          style="width: 100%"
        >
          <el-table-column
            label="No"
            width="50"
          >
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column
            label="Event"
            min-width="200"
          >
            <template slot-scope="{ row }">
              {{ printEventName(row.eventTypeLineName) }}
            </template>
          </el-table-column>

          <el-table-column
            width="422"
            prop="schedule"
            label="Schedule"
          >
            <template slot-scope="{ row }">
              <el-date-picker
                v-model="row.schedule"
                :disabled="readOnly"
                :format="dateDisplayFormat"
                end-placeholder="End Datetime"
                range-separator="To"
                size="mini"
                start-placeholder="Start Datetime"
                type="datetimerange"
                @change="value => onScheduleChanged(row, value)"
              ></el-date-picker>
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            label="Attachments"
            min-width="100"
          >
            <template slot-scope="{ row }">
              <!-- User can add attachments once the bidding is started. -->
              <el-button
                :disabled="!readOnly"
                icon="el-icon-paperclip"
                size="mini"
                type="primary"
                @click="editAttachments(row)"
              >Upload</el-button>
            </template>
          </el-table-column>

        </el-table>
      </el-col>
    </el-row>

    <el-row>
      <el-col
        :xs="24"
        :sm="18"
        :xl="16"
      >
        <el-divider content-position="left"><h4>Document Submission Schedule</h4></el-divider>
        <el-table
          v-loading="processing"
          ref="documentSchedules"
          border
          :data="bidding.documentSchedules"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          highlight-current-row
          size="mini"
          stripe
          style="width: 100%; height: 100%"
        >

          <el-table-column
            label="No"
            min-width="50"
          >
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column
            label="Doc. Event"
            min-width="200"
            prop="docEvent"
          ></el-table-column>

          <el-table-column
            label="Vendor Submission"
            min-width="310"
          >
            <template slot-scope="{ row }">
              <el-tag
                effect="plain"
                size="small"
                type="info"
              >
                Start: {{ row.vendorSubmissionStartDate | formatDate }}
              </el-tag>
              <el-tag
                effect="plain"
                size="small"
                type="info"
              >
                End: {{ row.vendorSubmissionEndDate | formatDate }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column
            label="Vendor Evaluation"
            min-width="310"
          >
            <template slot-scope="{ row }">
              <el-tag
                effect="plain"
                size="small"
                type="info"
              >
                Start: {{ row.vendorEvaluationStartDate | formatDate }}
              </el-tag>
              <el-tag
                effect="plain"
                size="small"
                type="info"
              >
                End: {{ row.vendorEvaluationEndDate | formatDate }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column
            v-if="!readOnly"
            fixed="right"
            min-width="56"
          >
            <template slot="header">
              <el-button
                icon="el-icon-plus"
                size="mini"
                type="primary"
                @click="addDocument"
              ></el-button>
            </template>
            <template slot-scope="{ row, $index }">
              <el-button
                icon="el-icon-delete"
                size="mini"
                type="danger"
                @click="removeDocument(row, $index)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog
      class="attachment-form"
      width="30%"
      :visible.sync="eventAttachmentVisible"
      title="Edit Attachments"
    >
      <el-upload
        action="/api/c-attachments/upload"
        drag
        :on-change="onAttachmentChanged"
        :on-preview="onAttachmentPreviewed"
        :on-remove="onAttachmentRemoved"
        :file-list="tmpAttachments"
        multiple
      >
        <em class="el-icon-upload"></em>
        <div class="el-upload__text">
          Drop file here or <em>click to upload</em>
        </div>
        <div
          class="el-upload__tip"
          slot="tip"
        >Upload one or more files</div>
      </el-upload>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          style="margin-left: 0px;"
          type="danger"
          @click="eventAttachmentVisible = false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="dialogConfirmationVisible"
      title="Document Submission Schedule"
    >
      <div>
        <el-form
          ref="documentSchedule"
          label-position="left"
          label-width="150px"
          :model="documentSchedule"
          size="mini"
        >
          <el-form-item
            label="Document Event"
            prop="docEvent"
            required
          >
            <el-input
              v-model="documentSchedule.docEvent"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Submission Date"
            prop="vendorSubmissionId"
            required
          >
            <el-select
              v-model="documentSchedule.vendorSubmissionId"
              class="form-input"
              clearable
              filterable
              placeholder="Select Submission Schedule"
              style="width: 100%"
            >
              <el-option
                v-for="item in eventScheduleOptions"
                :key="item.id"
                :label="printScheduleLabel(item)"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="Evaluation Date"
            prop="vendorEvaluationId"
            required
          >
            <el-select
              v-model="documentSchedule.vendorEvaluationId"
              class="form-input"
              clearable filterable
              placeholder="Select Evaluation Schedule"
              style="width: 100%"
            >
              <el-option
                v-for="item in eventScheduleOptions"
                :key="item.id"
                :label="printScheduleLabel(item)"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          style="margin-left: 0px;"
          @click="dialogConfirmationVisible = false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          style="margin-left: 0px;"
          size="mini"
          icon="el-icon-check"
          type="primary"
          @click="saveDocument"
        >
          {{ $t('entity.action.save') }}
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./bidding-schedule.component.ts"></script>

<style lang="scss">
.compact .bidding-schedule .el-table--mini {
  th, td {
    height: 35px;
  }
}

.bidding-schedule {
  .attachment-form {
    .el-upload {
      width: 100%;

      .el-upload-dragger {
        width: 100%;
      }
    }
  }

  .el-upload-list__item-name {
    font-weight: 400;
  }
}
</style>

<style lang="scss" scoped>
.bidding-schedule {
  .el-tag {
    border-radius: 12px;
    margin: 4px 0;

    .el-tag--success {
      background: #80b600;
    }
  }
}
</style>
