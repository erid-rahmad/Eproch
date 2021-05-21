<template>
  <el-dialog
    class="participant-list"
    title="Attendees"
    width="50%"
    :visible="visible"
    @close="onDialogClose"
    @open="onDialogOpen"
  >
    <template>
      <el-table
        v-if="index"
        v-loading="loading"
        ref="mainGrid"
        border
        :data="participants"
        highlight-current-row
        size="mini"
        stripe
      >
        <el-table-column width="50" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="Vendor Name"
          min-width="200"
          prop="vendorName"
          show-overflow-tooltip
        ></el-table-column>

        <el-table-column
          label="Registered Date"
          min-width="150"
        >
          <template slot-scope="{ row }">
            {{ row.registerDate | formatDate }}
          </template>
        </el-table-column>

        <el-table-column
          label="Attended"
          width="100"
        >
          <template slot-scope="{ row }">
            <el-checkbox
              v-model="row.attended"
              size="mini"
              @change="onAttendStatusChange(row)"
            ></el-checkbox>
          </template>
        </el-table-column>
      </el-table>
      <el-transfer
        v-else
        v-model="attendedParticipants"
        :data="availableVendors"
        filterable
        :titles="['Available Vendors', 'Included Vendors']"
        @change="onAttendeeChanged"
      >
      </el-transfer>
      <div slot="footer">
        <el-button
          style="margin-left: 0px;"
          size="mini"
          :icon="index ? 'el-icon-close' : 'el-icon-back'"
          :type="index ? null : 'danger'"
          @click="onCancel"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>

        <el-button
          v-if="index"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          @click="onAdd"
        >
          <svg-icon name="edit"></svg-icon> Edit
        </el-button>
        <el-button
          v-else
          :loading="updatingAttendees"
          :disabled="noUpdate"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          @click="onSave"
        >
          <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" src="./participant-list.component.ts"></script>
<style lang="scss">
.compact .participant-list {
  .el-table--mini {

    th,
    td {
        height: 35px;
    }
  }

  .el-transfer {
    display: flex;
    flex-direction: row;
    align-content: space-around;

    .el-transfer__buttons {
      display: flex;
      flex-direction: column;
      align-self: center;
      padding: 0 8px;

      .el-button--medium {
        padding: 5px 10px;
      }

      .el-button + .el-button {
        margin-left: 0;
      }
    }

    .el-transfer-panel {
      flex: 1;

      .el-checkbox__label {
        font-size: 12px;
      }

      .el-transfer-panel__header .el-checkbox .el-checkbox__label {
        font-size: 14px;
        font-weight: 600;
      }
    }
  }
}
</style>