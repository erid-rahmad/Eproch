<template>
    <div class="app-container">

        <el-divider content-position="left"><h4>Event Schedule</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="18">
                <el-table
                    v-loading="processing"
                    ref="biddingSchedule"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="biddingSchedule.eventSchedule">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="event"
                        label="Event"/>

                    <el-table-column
                        min-width="100"
                        prop="startDate"
                        label="Start Date">
                        <template slot-scope="{ row }">
                            <el-date-picker
                                style="width: 100%"
                                class="form-input"
                                size="mini"
                                clearable
                                v-model="row.startDate"
                                format="dd/MM/yyyy"
                                value-format="yyyy-MM-dd"
                                type="date"
                                placeholder="Pick a day"
                            />
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="endDate"
                        label="End Date">
                        <template slot-scope="{ row }">
                            <el-date-picker
                                style="width: 100%"
                                class="form-input"
                                size="mini"
                                clearable
                                v-model="row.endDate"
                                format="dd/MM/yyyy"
                                value-format="yyyy-MM-dd"
                                type="date"
                                placeholder="Pick a day"
                            />
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>

        <el-divider content-position="left"><h4>Document Submission Schedule</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="18">
                <el-table
                    v-loading="processing"
                    ref="biddingSchedule"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="biddingSchedule.documentSchedule">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="docEvent"
                        label="Doc. Event"/>

                    <el-table-column
                        min-width="100"
                        label="Vendor Submission">
                        <template slot-scope="{ row }">
                            {{ row.vendorSub.event }}
                            <br/>
                            Start: {{ row.vendorSub.startDate }} - End: {{ row.vendorSub.endDate }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        label="Vendor Evaluation">
                        <template slot-scope="{ row }">
                            {{ row.vendorEval.event }}
                            <br/>
                            Start: {{ row.vendorEval.startDate }} - End: {{ row.vendorEval.endDate }}
                        </template>
                    </el-table-column>

                    <el-table-column align="center" min-width="50">
                        <template slot="header">
                            <el-button
                                size="mini"
                                icon="el-icon-plus"
                                type="primary"
                                @click="addDocument"/>
                        </template>
                        <template slot-scope="row">
                            <el-button
                                size="mini"
                                icon="el-icon-delete"
                                type="danger"
                                @click="removeDocument(row.$index)"/>
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>

        <el-dialog
            width="50%"
            :visible.sync="dialogConfirmationVisible"
            title="Add Document Schedule">

            <template>
                <div>
                    <el-form ref="documentSchedule" label-position="left" label-width="150px" size="mini" :model="documentSchedule">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Doc. Event" prop="docEvent" required>
                                    <el-input class="form-input" clearable v-model="documentSchedule.docEvent" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Vendor Submission" prop="vendorSubmission" required>
                                    <el-select
                                        style="width: 100%"
                                        v-model="documentSchedule.vendorSubmission"
                                        class="form-input"
                                        clearable filterable
                                        :placeholder="$t('register.form.select')">
                                        <el-option
                                            v-for="item in eventScheduleOptions"
                                            :key="item.id"
                                            :label="item.event + ' (Start: ' + item.startDate + ' - End: ' + item.endDate + ')'"
                                            :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Vendor Evaluation" prop="vendorEvaluation" required>
                                    <el-select
                                        style="width: 100%"
                                        v-model="documentSchedule.vendorEvaluation"
                                        class="form-input"
                                        clearable filterable
                                        :placeholder="$t('register.form.select')">
                                        <el-option
                                            v-for="item in eventScheduleOptions"
                                            :key="item.id"
                                            :label="item.event + ' (Start: ' + item.startDate + ' - End: ' + item.endDate + ')'"
                                            :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="saveDocument">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisible = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./bidding-schedule.component.ts"></script>
