<template>
  <div class="bidding-schedule">
    <el-form
      disabled
      label-position="left"
      label-width="150px"
      :model="bidding"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item
            label="Title"
            prop="name"
            required
          >
            <el-input
              v-model="bidding.name"
              class="form-input"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Bidding No"
            prop="biddingNo"
          >
            <el-input
              v-model="bidding.documentNo"
              class="form-input"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col :span="24">
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
            prop="eventTypeLineName"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column label="Schedule" align="center">
            <el-table-column
              width="422"
              prop="schedule"
              label="Plan"
              align="center"
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
              width="422"
              prop="actual"
              label="Actual"
              align="center"
            >
              <template slot-scope="{ row }">
                <el-date-picker
                  v-model="row.actual"
                  disabled
                  :format="dateDisplayFormat"
                  end-placeholder="End Datetime"
                  range-separator="To"
                  size="mini"
                  start-placeholder="Start Datetime"
                  type="datetimerange"
                ></el-date-picker>
              </template>
            </el-table-column>
          </el-table-column>

          <el-table-column
            label="Status"
            min-width="150"
            align="center"
          >
            <template slot-scope="{ row }">
              {{ printStatus(row.status) }}
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            label="Action"
            min-width="200"
          >
            <template slot-scope="{ row }">
              <el-button
                size="mini"
                @click="editSchedule(row)"
              >
                <svg-icon name="icomoo/084-calendar"></svg-icon> Date
              </el-button>

              <el-button
                v-if="row.status && row.status !== 'N'"
                size="mini"
                type="primary"
                @click="viewEvent(row)"
              >
                <svg-icon name="link"></svg-icon> View
              </el-button>
            </template>
          </el-table-column>

        </el-table>
      </el-col>
    </el-row>

    <date-setting-form
      :visible.sync="editScheduleVisible"
      :schedule-id="selectedEvent.id"
      :title="selectedEvent.eventTypeLineName"
      @saved="onDateSettingSaved"
      @error="onDateSettingError"
    ></date-setting-form>

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
