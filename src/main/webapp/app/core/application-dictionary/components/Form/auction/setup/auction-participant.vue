<template>
  <div class="auction-participant">
    <el-form
      disabled
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="auction"
      :size="formSettings.size"
    >
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Auction No.">
            <el-input
              v-model="auction.documentNo"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Title">
            <el-input
              v-model="auction.name"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table
      v-loading="loadingParticipants"
      border
      :data="participants"
      highlight-current-row
      size="mini"
      stripe
    >
      <el-table-column width="50" label="No">
        <template slot-scope="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>

      <el-table-column
        label="Vendor"
        min-width="250"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          <el-tooltip
            v-if="row.editing"
            :content="row.vendorIdMessage"
            :disabled="!row.vendorIdError"
          >
            <el-select
              v-model="row.vendorId"
              :class="{ 'is-error': row.vendorIdError }"
              clearable
              filterable
              remote
              :remote-method="retrieveVendors"
              placeholder="Select Vendor"
              size="mini"
              @change="value => onVendorChanged(row, value)"
            >
              <el-option
                v-for="vendor in vendorOptions"
                :key="vendor.id"
                :label="vendor.name"
                :value="vendor.id"
              >
                <span style="float: left">{{ vendor.code }}</span>
                <span style="float: right; color: #8492a6; font-size: 12px">{{ vendor.name }}</span>
              </el-option>
            </el-select>
          </el-tooltip>
          <template v-else>
            {{ row.vendorName }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        label="User Contact"
        min-width="200"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          <el-tooltip
            v-if="row.editing"
            :content="row.userUserIdMessage"
            :disabled="!row.userUserIdError"
          >
            <el-select
              v-model="row.userUserId"
              :class="{ 'is-error': row.userUserIdError }"
              clearable
              :disabled="!row.vendorId"
              filterable
              remote
              :remote-method="query => retrieveVendorUsers(row.vendorId, query)"
              placeholder="Select Contact"
              size="mini"
              @change="value => onUserChanged(row, value)"
            >
              <el-option
                v-for="user in userOptions"
                :key="user.id"
                :label="user.name"
                :value="user.id"
              >
                <span style="float: left">{{ user.userLogin }}</span>
                <span style="float: right; color: #8492a6; font-size: 12px">{{ user.name }}</span>
              </el-option>
            </el-select>
          </el-tooltip>
          <template v-else>
            {{ row.userName }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        label="Business Email"
        min-width="200"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          {{ row.editing ? printEmail(row.userUserId) : row.userEmail }}
        </template>
      </el-table-column>

      <el-table-column
        v-if="!readOnly"
        align="right"
        width="200"
      >
        <template slot="header">
          <el-button
            v-show="!editMode"
            icon="el-icon-plus"
            size="mini"
            title="Add an Item"
            type="primary"
            @click="onAddClicked"
          ></el-button>
        </template>
        <template slot-scope="{ row, $index }">
          <el-button
            v-if="row.editing"
            icon="el-icon-close"
            size="mini"
            type="danger"
            @click="onEditCanceled($index)"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            v-else
            :disabled="editMode"
            icon="el-icon-edit"
            size="mini"
            type="primary"
            @click="onEditClicked(row)"
          >
            {{ $t('entity.action.edit') }}
          </el-button>

          <el-button
            v-if="row.editing"
            icon="el-icon-check"
            size="mini"
            type="primary"
            @click="onSaveClicked(row)"
          ></el-button>
          <el-button
            v-else
            :disabled="editMode"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="onDeleteClicked(row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      width="30%"
      :visible.sync="deleteConfirmationVisible"
      :title="$t('entity.delete.title')"
    >
      <template>
        <span>Are you sure to delete the selected record?</span>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="deleteConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-delete"
            type="danger"
            @click="deleteRecord"
          >
            {{ $t('entity.action.delete') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./auction-participant.component.ts"></script>
