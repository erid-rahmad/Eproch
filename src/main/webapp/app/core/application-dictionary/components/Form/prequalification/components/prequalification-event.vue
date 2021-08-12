<template>
  <div class="prequalification-event">
    <el-form label-position="left" label-width="150px" :model="preq" size="mini">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <el-form-item label="Title" prop="name" required>
            <el-input v-model="preq.name" disabled class="form-input"></el-input>
          </el-form-item>
          <el-form-item label="Prequistion No" prop="documentNo">
            <el-input v-model="preq.documentNo" disabled class="form-input"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <div>
            <el-form-item label="Prequalification Event">
              <el-select
                :disabled="readOnly"
                v-model="preq.event.eventId"
                class="form-input"
                clearable
                filterable
                placeholder="Select"
                @change="updatePrequalificationSteps()"
              >
                <el-option v-for="item in eventOptions" :key="item.key" :label="item.value" :value="item.key"></el-option>
              </el-select>
            </el-form-item>
            <el-button size="mini" type="primary" @click="loadPic()"> Select PIC </el-button>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <div>
            <el-form-item label="Prequalification Method">
              <el-select :disabled="readOnly" v-model="preq.event.methodId" class="form-input" clearable filterable placeholder="Select">
                <el-option v-for="item in methodOptions" :key="item.key" :label="item.value" :value="item.key"></el-option>
              </el-select>
            </el-form-item>
            <el-button size="mini" type="primary" @click="loadDetail()"> Method Detail </el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col :span="24">
        <el-divider content-position="left"><h4>Event Steps</h4></el-divider>
        <el-table
          v-loading="processing"
          ref="eventSteps"
          border
          :data="eventSteps"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          highlight-current-row
          size="mini"
          stripe
          style="width: 100%"
        >
          <el-table-column label="No" width="50">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column label="Step" min-width="200" prop="prequalificationStepName" show-overflow-tooltip></el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-dialog :visible.sync="showPic" title="PIC Detail" width="50%">
      <el-table :data="members" border class="member-list" highlight-current-row size="mini">
        <el-table-column label="No." width="50">
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>
      <el-table-column label="PIC Name" min-width="320" prop="adUserName" show-overflow-tooltip/>
      <el-table-column label="Department" prop="adUserPosition" width="150"/>
      <el-table-column label="Task" width="150">
        <template slot-scope="{ row }">
            {{ formatPosition(row.position) }}
          </template>
      </el-table-column>
      <el-table-column label="Email" prop="adUserEmail" width="150"/>
    </el-table>
    </el-dialog>
    <el-dialog :visible.sync="showDetail" title="Method Detail" width="50%">
      <el-form label-position="left" label-width="128px" :loading="loadingDetails">
        <el-row
          v-for="(line, index) in evaluationMethodCriteria"
          :key="line.id"
          :class="`line-${index}`"
          class="criteria-section"
        >
          <el-row
            v-for="(criteria, index) in line.criteria"
            :key="criteria.id"
            :class="`criteria2-${index}`"
            class="criteria-section"
          >
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
                  <el-row
                  >
                    <el-table :data="subCriteria.questions" border class="question-list" highlight-current-row size="mini">
                      <el-table-column label="No." width="50">
                        <template slot-scope="{ $index }">
                          {{ $index + 1 }}
                        </template>
                      </el-table-column>
                      <el-table-column label="Question" min-width="320" prop="name" show-overflow-tooltip></el-table-column>
                      <el-table-column label="Requirement" prop="requirement" width="150">
                        <template slot-scope="{ row }">
                          <el-input
                            v-model="row.requirement"
                            :disabled="readOnly"
                            class="form-input"
                            clearable
                            size="mini"
                          ></el-input>
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
      </el-form>
      <div slot="footer">
        <el-button
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

<script lang="ts" src="./prequalification-event.component.ts"></script>

<style lang="scss">
.compact .prequalification-event .el-table--mini {
  th,
  td {
    height: 35px;
  }
}

.prequalification-event {
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
.prequalification-event {
  .el-tag {
    border-radius: 12px;
    margin: 4px 0;

    .el-tag--success {
      background: #80b600;
    }
  }
}
</style>
