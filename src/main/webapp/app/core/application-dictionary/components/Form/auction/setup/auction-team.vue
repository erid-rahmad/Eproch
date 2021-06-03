<template>
  <div class="auction-team">
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
      v-loading="loadingTeams"
      border
      :data="teams"
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
        label="Role"
        min-width="150"
        prop="role"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          {{ printRoleName(row.role) }}
        </template>
      </el-table-column>

      <el-table-column
        label="User"
        min-width="200"
        prop="userName"
        show-overflow-tooltip
        sortable
      ></el-table-column>

      <el-table-column
        v-if="!readOnly"
        align="right"
        width="200"
      >
        <template slot="header">
          <el-button
            icon="el-icon-plus"
            size="mini"
            title="Add a User"
            type="primary"
            @click="teamMemberFormVisible = true"
          ></el-button>
        </template>
        <template slot-scope="{ row }">
          <el-button
            icon="el-icon-edit"
            size="mini"
            type="primary"
            @click="onEditClicked(row)"
          >
            {{ $t('entity.action.edit') }}
          </el-button>
          <el-button
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

    <el-dialog
      :show-close="false"
      title="Add a Member"
      :visible.sync="teamMemberFormVisible"
      @close="onTeamMemberFormClose"
      @open="onTeamMemberFormOpen"
      @opened="onTeamMemberFormOpened"
    >
      <template>
        <el-form
          ref="teamMemberForm"
          :label-position="formSettings.labelPosition"
          :label-width="formSettings.labelWidth"
          :model="teamMember"
          :rules="teamMemberValidationSchema"
          :size="formSettings.size"
        >
          <el-form-item
            label="Role"
            prop="role"
          >
            <el-select
              v-model="teamMember.role"
              ref="memberRole"
              clearable
              filterable
              placeholder="Select Role"
            >
              <el-option
                v-for="item in roleOptions"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="User"
            prop="userUserId"
          >
            <el-select
              v-model="teamMember.userUserId"
              clearable
              filterable
              placeholder="Select User"
            >
              <el-option
                v-for="item in userOptions"
                :key="item.id"
                :label="item.userLogin"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>

        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            @click="teamMemberFormVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            icon="el-icon-check"
            size="mini"
            type="primary"
            @click="saveMember"
          >
            {{ $t('entity.action.save') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./auction-team.component.ts"></script>
