<template>
  <div class="auction-item">
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
            <span>{{ auction.documentNo }}</span>
          </el-form-item>
          <el-form-item label="Title">
            <span>{{ auction.name }}</span>
          </el-form-item>
          <el-form-item label="Description">
            <span>{{ auction.description }}</span>
          </el-form-item>
          <el-form-item label="Currency">
            <span>{{ auction.currencyName }}</span>
          </el-form-item>
          <el-form-item label="Owner">
            <span>{{ auction.ownerUserName }}</span>
          </el-form-item>
          <el-form-item label="Department">
            <span>{{ auction.costCenterName }}</span>
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
          <el-form-item label="Creation Date">
            <span>{{ auction.createdDate | formatDate(false) }}</span>
          </el-form-item>
          <el-form-item label="Last Modified Date">
            <span>{{ auction.lastModifiedDate | formatDate(false) }}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-divider content-position="left">
      <h4>Team Members</h4>
    </el-divider>
    <el-table
      v-loading="loadingTeams"
      border
      :data="teams"
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
        label="Role"
        width="150"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          {{ printRoleName(row.role) }}
        </template>
      </el-table-column>

      <el-table-column
        label="User"
        width="200"
        prop="userName"
        show-overflow-tooltip
        sortable
      ></el-table-column>
    </el-table>

  </div>
</template>
<script lang="ts" src="./auction-summary.component.ts"></script>
