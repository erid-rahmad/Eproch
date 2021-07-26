<template>
    <div>
    <div class="contract-document" v-if="indexs">
        <el-table
            ref="documents"
            v-loading="loading"
            :data="ContactTasks"
            border
            highlight-current-row
            size="mini"
            stripe
        >
            <el-table-column
                label="No" width="50">
                <template v-slot="{ $index }">
                    {{ $index + 1 }}
                </template>
            </el-table-column>

            <el-table-column
                label="Document Name"
                min-width="200"
                prop="name"
                show-overflow-tooltip
            ></el-table-column>
            <el-table-column
                label="Document Name"
                min-width="200"
                prop="name"
                show-overflow-tooltip
            ></el-table-column>
            <el-table-column
                label="Document Name"
                min-width="200"
                prop="name"
                show-overflow-tooltip
            ></el-table-column>
            <el-table-column
                label="Document Name"
                min-width="200"
                prop="name"
                show-overflow-tooltip
            ></el-table-column>
            <el-table-column
            >
                <template slot-scope="{row}" >
                <el-button size="mini" style="margin-left: 0px;" @click="view(row)"
                >view
                </el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-dialog
            :show-close="false"
            :visible.sync="documentFormVisible"
            title="Upload Document"
            width="60%"
        >

        </el-dialog>
        <el-dialog
            :show-close="false"
            :visible.sync="contractTextVisible"
            title="Contract"
            width="60%"
        >
            <td v-html="contractText"></td>
            <div slot="footer">
                <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                           @click="contractTextVisible = false">
                    {{ $t('entity.action.cancel') }}
                </el-button>
            </div>

        </el-dialog>
    </div>
    <div v-if="!indexs" >
        <el-form
            ref="contractInfoForm"
            v-loading="loading"

            class="contract-info"
        >

            <!--            :disabled="readOnly"-->
            <!--            :label-position="formSettings.labelPosition"-->
            <!--            :label-width="formSettings.labelWidth"-->
            <!--            :model="contract"-->
            <!--            :rules="validationSchema"-->
            <!--            :size="formSettings.size"-->

            <el-form-item label="Select Review">
                <el-input
                    ref="documentNo"
                    v-model="ContactTask.documentNo"

                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="Approval Rule Flow Type">
                <el-input
                    ref="documentNo"
                    v-model="ContactTask.documentNo"

                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="Specify Due Date">
                <el-input
                    ref="documentNo"
                    v-model="ContactTask.documentNo"

                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="Provide an innitial message and click submit or mark cancelled ">
                <el-input
                    ref="documentNo"
                    v-model="ContactTask.documentNo"

                    clearable
                ></el-input>
            </el-form-item>


        </el-form>
    </div>
    </div>
</template>
<script lang="ts" src="./task.component.ts"></script>
