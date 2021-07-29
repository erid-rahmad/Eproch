<template>
  <div class="contract-team">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="mainForm"
      :rules="validationSchema"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col :span="8">
          <el-form-item label="Contract No.">
            {{ mainForm.documentNo || mainForm.contractNo }}
          </el-form-item>
          <el-form-item label="Title">
            {{ mainForm.name || mainForm.contractName }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col
        :xs="24"
        :sm="18"
      >
        <el-table
          v-loading="loading"
          ref="memberTable"
          border
          class="member-table"
          :data="mainForm.members"
          highlight-current-row
          :max-height="gridSchema.maxHeight"
          size="mini"
          stripe
          @current-change="onCurrentRowChanged"
        >

          <el-table-column
            label="Position"
            width="200"
            show-overflow-tooltip
          >
            <template slot-scope="{ row }">
              <el-select
                v-if="row.editMode"
                v-model="row.position"
                clearable
                filterable
                size="mini"
              >
                <el-option
                  v-for="position in positions"
                  :key="position.value"
                  :label="position.name"
                  :value="position.value"
                ></el-option>
              </el-select>
              <template v-else>
                {{ formatPosition(row.position) }}
              </template>
            </template>
          </el-table-column>

          <el-table-column
            label="User"
            width="200"
            show-overflow-tooltip
          >
            <template slot-scope="{ row }">
              <el-select
                v-if="row.editMode"
                v-model="row.adUserId"
                clearable
                filterable
                remote
                :remote-method="retrieveUsers"
                size="mini"
                @change="value => onUserChanged(row, value)"
              >
                <el-option
                  v-for="user in userOptions"
                  :key="user.id"
                  :label="user.name"
                  :value="user.id"
                >
                  <span style="float: left">{{ user.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 12px">{{ user.position }}</span>
                </el-option>
              </el-select>
              <template v-else>
                {{ row.adUserName }}
              </template>
            </template>
          </el-table-column>

          <el-table-column
            label="Position"
            prop="adUserPosition"
            width="200"
          ></el-table-column>

          <el-table-column
            align="right"
            fixed="right"
            label="Action"
            min-width="200"
          >
            <template slot="header">
              <el-button
                :disabled="editMode"
                icon="el-icon-plus"
                size="mini"
                type="primary"
                @click="addMember"
              ></el-button>
            </template>
            <template slot-scope="{ row, $index }">
              <el-button
                v-if="row.editMode"
                icon="el-icon-check"
                size="mini"
                type="primary"
                @click="saveMember(row)"
              ></el-button>
              <el-button
                v-else
                icon="el-icon-edit"
                size="mini"
                type="primary"
                @click="editMember(row)"
              ></el-button>
              <el-button
                v-if="row.editMode"
                icon="el-icon-close"
                size="mini"
                type="danger"
                @click="cancelEdit($index)"
              ></el-button>
              <el-button
                v-else
                icon="el-icon-delete"
                size="mini"
                type="danger"
                @click="deleteMember($index)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./contract-team.component.ts"></script>
<style lang="scss" scoped>
.compact .el-table .el-button {
  margin: 5px 0;
}
</style>
