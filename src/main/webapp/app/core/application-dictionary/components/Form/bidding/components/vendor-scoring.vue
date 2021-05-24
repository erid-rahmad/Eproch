<template>
  <div class="vendor-scoring">
    <el-form
      ref="biddingInformation"
      :model="bidding"
      disabled
      label-position="left"
      label-width="150px"
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

    <el-form
      ref="mainForm"
      :model="mainForm"
      label-position="left"
      label-width="150px"
      :rules="validationSchema"
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
            label="Evaluation Method"
            prop="evaluationMethodId"
          >
            <template>
              <el-select
                v-model="mainForm.evaluationMethodId"
                :disabled="readOnly"
                filterable
                placeholder="Select"
                @change="onEvaluationMethodChange"
              >
                <el-option
                  v-for="item in evaluationMethods"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </template>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="24">
      <el-col :span="20">
        <el-table
          border
          :data="evaluationMethodLines"
          highlight-current-row
          size="mini"
          stripe
        >
          <el-table-column
            label="No"
            width="50">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            label="Evaluation"
            min-width="200"
            show-overflow-tooltip
          >
            <template slot-scope="{ row }">
              {{ printEvaluation(row.evaluation) }}
            </template>
          </el-table-column>
          <el-table-column
            label="Evaluation Type"
            min-width="200"
            show-overflow-tooltip
          >
            <template slot-scope="{ row }">
              {{ printEvaluationType(row.evaluationType) }}
            </template>
          </el-table-column>
          <el-table-column
            label="Weight"
            min-width="100"
          >
            <template slot-scope="{ row }">
              {{ row.weight }}
            </template>
          </el-table-column>
          <el-table-column
            label="Passing Grade"
            min-width="150"
          >
            <template slot-scope="{ row }">
              {{ row.passingGrade }}
            </template>
          </el-table-column>
          <el-table-column width="130">
            <template slot="header">&nbsp;</template>
            <template slot-scope="{ row }">
              <el-button
                v-if="row.evaluation !== 'P'"
                :disabled="!row.evaluationMethodLineId"
                size="mini"
                type="primary"
                @click="editRequirement(row)"
              >
                <svg-icon name="edit"></svg-icon> Requirement
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog
      :visible.sync="criteriaPA"
      title="Criteria"
      width="80%"
      @close="requirementsUpdated = false"
    >
      <prequalification-form
        ref="prequalificationForm"
        :data="selectedRow"
        :loading.sync="savingRequirements"
        :read-only="readOnly"
        @saved="closeCriteriaPA"
        @updated="requirementsUpdated = true"
      ></prequalification-form>

      <div slot="footer">
        <el-button
          :loading="savingRequirements"
          :disabled="!requirementsUpdated"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          @click="saveRequirements"
        >
          <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./vendor-scoring.component.ts"></script>

<style lang="scss">
.compact .vendor-scoring .el-table--mini {
  th,
  td {
    height: 35px;
  }
}
</style>
