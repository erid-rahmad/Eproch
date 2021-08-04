<template>
  <div class="evaluation-form">
    <h3 style="margin-top: 0">Prequalification Submission</h3>
    <el-form ref="mainForm" label-position="left" label-width="200px" size="mini" v-loading="loading">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <el-form-item label="Title" prop="name" required>
            <el-input v-model="data.prequalificationName" disabled class="form-input"></el-input>
          </el-form-item>
          <el-form-item label="Prequalification No" prop="documentNo">
            <el-input v-model="data.prequalificationNo" disabled class="form-input"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item class="criteria-label" label="Status">
        {{ proposalStatus }}
      </el-form-item>
      <el-row v-for="(line, index) in evaluationMethodCriteria" :key="line.id" :class="`line-${index}`" class="criteria-section">
        <el-row v-for="(criteria, index) in line.criteria" :key="criteria.id" :class="`criteria2-${index}`" class="criteria-section">
          <el-col :span="24">
            <el-form-item class="criteria-label" label="Criteria">
              {{ criteria.biddingCriteriaName }}
            </el-form-item>
            <el-row
              v-for="(subCriteria, subIndex) in criteria.subCriteria"
              :key="subCriteria.id"
              :class="`sub-${subIndex}`"
              class="sub-criteria-section"
            >
              <el-col :span="24">
                <el-row>
                  <el-col :span="12">
                    <div class="grid-content bg-purple">
                      <el-form-item label="Sub Criteria">
                        {{ subCriteria.biddingSubCriteriaName }}
                      </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col style="text-align: right;padding-right: 40px;padding-bottom: 5px">
                    <el-button v-if="subCriteria.attachmentName" icon="el-icon-view" size="mini" type="primary"
                      @click="handleDownload(subCriteria.attachmentUrl)">{{ subCriteria.attachmentName }}
                    </el-button>
                  </el-col>
                  <el-table :data="subCriteria.questions" border class="question-list" highlight-current-row size="mini">
                    <el-table-column label="No." width="50">
                      <template slot-scope="{ $index }">
                        {{ $index + 1 }}
                      </template>
                    </el-table-column>
                    <el-table-column label="Question" min-width="320" prop="name" show-overflow-tooltip></el-table-column>
                    <el-table-column label="Requirement" prop="requirement" width="150"> </el-table-column>
                    <el-table-column label="Answer" width="320">
                      <template slot-scope="{ row }">
                        <el-input v-model="row.answer" disabled size="mini"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="Evaluate" min-width="150">
                      <template slot-scope="{ row }">
                        <el-select v-model="row.passFail" placeholder="Pass Fail" disabled>
                          <el-option v-for="item in passfail" :key="item.value" :label="item.label" :value="item.value"/>
                        </el-select>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-divider />
                </el-row>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-row>
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <el-form-item label="Verdict" required>
            <el-select v-model="data.passFail" placeholder="Pass Fail" disabled>
              <el-option v-for="item in passfail" :key="item.value" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./eval-form-vendor.component.ts"></script>
<style lang="scss">
.evaluation-form {
  .criteria-label {
    font-weight: 700;
  }

  .sub-criteria-section {
    label.el-form-item__label {
      font-weight: 600;
    }
  }
}

.compact .evaluation-form {
  .el-table--mini {
    td,
    th {
      height: 35px;
    }
  }
}
</style>
<style lang="scss" scoped>
.evaluation-form {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
