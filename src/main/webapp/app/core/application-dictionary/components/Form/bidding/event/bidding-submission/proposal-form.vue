<template>
  <div class="proposal-form">
    <h3 style="margin-top: 0">{{ data.evaluation }} Proposal</h3>
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="200px"
      size="mini"
    >
      <el-row
        v-for="(criteria, index) in evaluationMethodCriteria"
        :key="criteria.id"
        class="criteria-section"
        :class="`criteria-${index}`"
      >
        <el-col :span="24">
          <el-row>
            <el-col :span="8">
              <el-form-item class="criteria-label" label="Criteria">
                {{ criteria.biddingCriteriaName }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row
            v-for="(subCriteria, subIndex) in criteria.evalMethodSubCriteriaList"
            :key="subCriteria.id"
            class="sub-criteria-section"
            :class="`sub-${subIndex}`"
          >
            <el-col :span="24">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="Sub Criteria">
                    {{ subCriteria.biddingSubCriteriaName }}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-button
                    size="mini"
                    type="primary"
                  >
                    <svg-icon name="icomoo/206-attachment"></svg-icon> Attachments
                  </el-button>
                </el-col>
              </el-row>
              <el-row
                v-for="(biddingSubCriteria, subsubindex) in subCriteria.biddingSubCriteriaDTO"
                :key="biddingSubCriteria.id"
                class="sub-sub-criteria-section"
                :class="`sub-${subsubindex}`"
              >
                <el-table
                  border
                  class="question-list"
                  :data="biddingSubCriteria.criteriaLineDTO"
                  highlight-current-row
                  size="mini"
                >
                  <el-table-column
                    label="No."
                    width="50"
                  >
                    <template slot-scope="{ $index }">
                      {{ $index + 1 }}
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="Question"
                    min-width="320"
                    prop="name"
                    show-overflow-tooltip
                    sortable
                  ></el-table-column>
                  <el-table-column
                    label="Requirement"
                    prop="requirement"
                    width="150"
                  ></el-table-column>

                  <el-table-column
                    label="Answer"
                    width="200"
                  >
                    <template slot-scope="{ row }">
                      <el-input
                        v-model="row.answer"
                        :readonly="!isVendor"
                        size="mini"
                      ></el-input>
                    </template>
                  </el-table-column>

                  <el-table-column
                    v-if="!isVendor"
                    label="Document"
                    width="100"
                  >
                    <template slot-scope="{ row }">
                      <el-checkbox
                        v-model="row.documentEvaluation"
                        size="mini"
                      ></el-checkbox>
                    </template>
                  </el-table-column>
                </el-table>
              </el-row>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./proposal-form.component.ts"></script>
<style lang="scss">
.proposal-form {
  .criteria-label {
    font-weight: 700;
  }

  .sub-criteria-section {
    label.el-form-item__label {
      font-weight: 600;
    }
  } 
}
.compact .proposal-form {
  .el-table--mini {
    td,
    th {
      height: 35px;
    }
  }
}
</style>
<style lang="scss" scoped>
.proposal-form {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
