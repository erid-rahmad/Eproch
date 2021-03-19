<template>
  <div class="bidding-schedule">

    <el-row>
      <el-col
        :xs="24"
        :sm="16"
      >
        <el-divider content-position="left"><h4>Event Schedule</h4></el-divider>
        <el-table
          v-loading="processing"
          ref="biddingSchedules"
          highlight-current-row
          border stripe
          size="mini"
          style="width: 100%"
          :height="gridSchema.height"
          :max-height="gridSchema.maxHeight"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          :data="bidding.biddingSchedules"
        >
          <el-table-column
            label="No"
            min-width="30"
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
              {{ printEventName(row.event) }}
            </template>
          </el-table-column>

          <el-table-column
            min-width="100"
            prop="startDate"
            label="Start Date"
          >
            <template slot-scope="{ row }">
              <el-date-picker
                style="width: 100%"
                class="form-input"
                size="mini"
                clearable
                v-model="row.startDate"
                format="dd/MM/yyyy HH:mm"
                value-format="yyyy-MM-dd HH:mm"
                type="datetime"
                placeholder="Pick a day"
              ></el-date-picker>
            </template>
          </el-table-column>

          <el-table-column
            min-width="100"
            prop="endDate"
            label="End Date"
          >
            <template slot-scope="{ row }">
              <el-date-picker
                style="width: 100%"
                class="form-input"
                size="mini"
                clearable
                v-model="row.endDate"
                format="dd/MM/yyyy HH:mm"
                value-format="yyyy-MM-dd HH:mm"
                type="datetime"
                placeholder="Pick a day"
              ></el-date-picker>
            </template>
          </el-table-column>

          <el-table-column
            label="Attachments"
            min-width="96"
          >
            <template slot-scope="{ row }">
              <el-button
                :disabled="!bidding.processed && !bidding.approved"
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
        :sm="16"
      >
        <el-divider content-position="left"><h4>Document Submission Schedule</h4></el-divider>
        <el-table
          v-loading="processing"
          ref="documentSchedules"
          highlight-current-row
          border stripe
          size="mini"
          style="width: 100%; height: 100%"
          :height="gridSchema.height"
          :max-height="gridSchema.maxHeight"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          :data="bidding.documentSchedules"
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
                Start: {{ row.vendorSubmission.startDate }}
              </el-tag>
              <el-tag
                effect="plain"
                size="small"
                type="info"
              >
                End: {{ row.vendorSubmission.endDate }}
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
                Start: {{ row.vendorEvaluation.startDate }}
              </el-tag>
              <el-tag
                effect="plain"
                size="small"
                type="info"
              >
                End: {{ row.vendorEvaluation.endDate }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column align="right" min-width="50">
            <template slot="header">
              <el-button
                size="mini"
                icon="el-icon-plus"
                style="margin: 4px 0"
                type="primary"
                @click="addDocument"/>
            </template>
            <template slot-scope="row">
              <el-button
                size="mini"
                icon="el-icon-delete"
                type="danger"
                @click="removeDocument(row.$index)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog
      width="50%"
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
    </el-dialog>

    <el-dialog
      width="50%"
      :visible.sync="dialogConfirmationVisible"
      title="Document Submission Schedule"
    >
      <template>
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
              prop="submissionScheduleId"
              required
            >
              <el-select
                v-model="documentSchedule.submissionScheduleId"
                class="form-input"
                clearable
                filterable
                placeholder="Select Submission Schedule"
                style="width: 100%"
              >
                <el-option
                  v-for="item in eventScheduleOptions"
                  :key="item.id"
                  :label="printEventName(item.event) + ' (Start: ' + item.startDate + ' - End: ' + item.endDate + ')'"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Evaluation Date" prop="evaluationScheduleId" required>
              <el-select
                v-model="documentSchedule.evaluationScheduleId"
                class="form-input"
                clearable filterable
                placeholder="Select Evaluation Schedule"
                style="width: 100%"
              >
                <el-option
                  v-for="item in eventScheduleOptions"
                  :key="item.id"
                  :label="printEventName(item.event) + ' (Start: ' + item.startDate + ' - End: ' + item.endDate + ')'"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>

        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-check"
            type="primary"
            @click="saveDocument"
          >
            Save
          </el-button>
          <el-button
            icon="el-icon-close"
            size="mini"
            style="margin-left: 0px;"
            @click="dialogConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./bidding-schedule.component.ts"></script>

<style lang="scss" scoped>
.bidding-schedule .el-tag {
  border-radius: 12px;
  margin: 4px 0;

  .el-tag--success {
    background: #80b600;
  }
}
</style>
