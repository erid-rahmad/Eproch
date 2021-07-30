<template>
  <div class="prequal-evaluation card-view">
    <el-row class="toolbar">
      <el-col :span="24">
        <el-button size="mini" style="margin-left: 10px" type="danger" @click="close"> Close </el-button>
        <el-button size="mini" style="margin-left: 10px" type="primary" :disabled="readOnly" @click="dialogSubmitEvaluation = true">
          Submit
        </el-button>
        <!--v-if="SelectVendorScoringLine.evaluationMethodLineEvaluation"-->
        <el-button size="mini" :disabled="readOnly" style="margin-left: 10px" type="primary" @click="save('DRF')"> Save </el-button>
        <el-dialog :visible.sync="dialogSubmitEvaluation" title="Tips" width="30%">
          <span>Submit Evaluation ?</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogSubmitEvaluation = false">Cancel</el-button>
            <el-button type="primary" @click="submitEvaluation">Confirm</el-button>
          </span>
        </el-dialog>
        <el-button
          v-if="readOnly"
          size="mini"
          :disabled="readOnlyApp"
          style="margin-left: 10px"
          type="primary"
          @click="dialogApproveEvaluation = true"
        >
          Approve
        </el-button>
        <el-dialog :visible.sync="dialogApproveEvaluation" title="Tips" width="30%">
          <span>Approve Evaluation ?</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogApproveEvaluation = false">Cancel</el-button>
            <el-button type="primary" @click="approveEvaluation">Confirm</el-button>
          </span>
        </el-dialog>
        <el-button
          v-if="readOnly"
          :disabled="readOnlyApp"
          size="mini"
          style="margin-left: 10px"
          type="primary"
          @click="dialogRejectEvaluation = true"
        >
          Reject
        </el-button>
        <template>
          <el-dialog :visible.sync="dialogRejectEvaluation" title="Tips" width="30%">
            <span>Reject Evaluation ?</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogRejectEvaluation = false">Cancel</el-button>
              <el-button type="primary" @click="rejectEvaluation">Confirm</el-button>
            </span>
          </el-dialog>
        </template>
      </el-col>
    </el-row>
    <div class="card">
      <header style="padding-bottom: 20px">
        <h3 align="center">Prequalification Submission</h3>
      </header>
      <el-form ref="mainForm" label-position="left" label-width="200px" size="mini" v-loading="loading">
        <el-row :gutter="24">
          <el-col :xs="24" :sm="24" :lg="12" :xl="8">
            <el-form-item label="Title" prop="name">
              <el-input v-model="data.prequalificationName" disabled class="form-input"></el-input>
            </el-form-item>
            <el-form-item label="Prequistion No" prop="documentNo">
              <el-input v-model="data.prequalificationNo" disabled class="form-input"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
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
                          <el-select v-model="row.passFail" placeholder="Pass Fail" :disabled="readOnly">
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
              <el-select v-model="data.passFail" placeholder="Pass Fail" :disabled="readOnly">
                <el-option v-for="item in passfail" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" src="./evaluation-form.component.ts"></script>

<style lang="scss">
.prequal-evaluation {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  td.document-submission .cell {
    padding: 0;

    .el-row .el-col.border {
      border-bottom: none !important;
      border-left: none !important;
      padding: 4px 10px;

      &:last-child {
        border-right: none !important;
      }
    }

    .el-row:first-child .el-col.border {
      border-top: none !important;
    }
  }

  .el-divider--horizontal {
    margin-top: 32px;
    margin-bottom: 16px;
  }

  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }

  .toolbar {
    padding: 4px;
  }

  .vendor-scoring tbody td {
    height: 35px;
  }
}

.el-tabs__header {
  margin: 0px;
}

.el-table__fixed {
  box-shadow: none;
}

.main {
  padding: 0px;
}

.form-input {
  width: 100%;
}
</style>
