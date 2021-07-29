<template>
    <div class="app-container card-view vendor-evaluation">
        <div class="card">
            <h5>Approval Activities</h5>
            <el-row :gutter="24">
                <el-col :span="24">
                    <el-table
                        v-loading="loading"
                        ref="mainGrid"
                        border
                        :data="vendors"
                        :default-sort="gridSchema.defaultSort"
                        :empty-text="gridSchema.emptyText"
                        @current-change="selectVendor"
                        highlight-current-row
                        size="mini"
                        stripe
                        style="width:100%;"
                    >
                        <el-table-column
                            label="Organization Name"
                            min-width="120"
                            prop="adOrganizationName"
                        >
                        </el-table-column>
                        <el-table-column
                            label="Name"
                            min-width="120"
                            prop="name"
                        >
                        </el-table-column>
                        <el-table-column
                            label="Document Status"
                            min-width="80"
                            prop="documentStatus"
                        >
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
        <div class="card">
            <el-form
                vm-model="model"
                ref=""
                label-width="200px"
            >
                <el-form-item label="Summary Records">
                    <el-input   
                        :value="summary"
                        type="textarea"
                         disabled
                         rows="10"
                    >
                    </el-input>
                </el-form-item>
                <el-form-item label="Lookup Document">
                    <el-col :span="6">
                        <el-button 
                            icon="el-icon-view"
                            type="primary"
                            size="small"
                            @click="viewDocument"
                        >View
                        </el-button>
                    </el-col>
                    <el-col :span="6">
                        <el-button
                            icon="el-icon-printer"
                            type="primary"
                            size="small"
                            @click="printDocument"
                        >
                        Print
                        </el-button>
                    </el-col>
                </el-form-item>
                <el-form-item label="Do you want to Approve ?">
                    <el-col :span="6">
                        <el-button
                            type="danger"
                            size="small"
                            @click="approve(false)"
                        >Reject
                        </el-button>
                    </el-col>
                    <el-col :span="6">
                        <el-button
                            type="primary"
                            size="small"
                            @click="approve(true)"
                        >Approve
                        </el-button>
                    </el-col>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script lang="ts" src="./vendor-approval.component.ts"></script>