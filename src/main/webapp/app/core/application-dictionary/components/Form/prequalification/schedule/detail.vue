<template>
  <div class="prequalification-schedule">
    <el-form
      disabled
      label-position="left"
      label-width="150px"
      :model="preq"
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
              v-model="preq.name"
              class="form-input"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Prequalification No"
            prop="biddingNo"
          >
            <el-input
              v-model="preq.documentNo"
              class="form-input"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row style="">
      <el-col :span="24">
        <el-divider content-position="left"><h4>Event Schedule</h4></el-divider>
        <el-table
          v-loading="processing"
          ref="preqSchedules"
          border
          :data="preq.preqSchedules"
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
            prop="eventLineName"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column label="Schedule">
            <el-table-column
              width="422"
              prop="schedule"
              label="Plan"
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
    <el-button size="mini" type="primary" @click="onFormSaved">
      <svg-icon name="icomoo/273-checkmark"></svg-icon>
        Save
    </el-button>
    <date-setting-form
      :visible.sync="editScheduleVisible"
      :schedule-id="selectedEvent.id"
      :title="selectedEvent.eventLineName"
      :prequalification-mode="true"
      @saved="onDateSettingSaved"
      @error="onDateSettingError"
    ></date-setting-form>

  </div>
</template>

<script lang="ts" src="./detail.component.ts"></script>

<style lang="scss">
.compact .prequalification-schedule .el-table--mini {
  th, td {
    height: 35px;
  }
}

.prequalification-schedule {
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
.prequalification-schedule {
  .el-tag {
    border-radius: 12px;
    margin: 4px 0;

    .el-tag--success {
      background: #80b600;
    }
  }
}
</style>
