<template>
    <div>
        <div class="contract-document">
            <el-table
                v-if="index===0"
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
                    label="Task Name"
                    min-width="200"
                    prop="name"
                    show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                    label="Status"
                    min-width="200"
                    prop="status"
                    show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                    label="Due Date"
                    min-width="200"
                    prop="dueDate"
                    show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                >
                    <template slot-scope="{row}">
                        <el-button v-if="!isVendor" size="mini" style="margin-left: 0px;" @click="view(row)"
                        >Edit
                        </el-button>
                        <el-button size="mini" style="margin-left: 0px;" @click="viewNego(row)"
                        >Detail
                        </el-button>
                    </template>
                </el-table-column>

            </el-table>
        </div>
        <div v-if="index===1">
            <el-form
                ref="contractInfoForm"
                v-loading="loading"
                :label-position="formSettings.labelPosition"
                :label-width="formSettings.labelWidth"
                :size="formSettings.size"
                class="contract-info"
            >
                <el-form-item label="Select Reviewers">
                    <el-button size="mini" style="margin-left: 0px;"
                               @click="addPic"> Add Reviewers
                    </el-button>
                    <template v-for="pic in mainForm.reviewers">
                        <ad-input-lookup
                            v-model="pic.picId"
                            :label-fields="['userLogin']"
                            :query="['employee.equals=true']"
                            placeholder="Select PiC"
                            table-name="ad_user"
                        ></ad-input-lookup>
                    </template>
                </el-form-item>
                <el-form-item label="Approval Rule Flow Type">
                    <el-select v-model="mainForm.contractTask.documentAction" placeholder="Select">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Specify Due Date">
                    <div class="block">
                        <span class="demonstration"></span>
                        <el-date-picker
                            v-model="mainForm.contractTask.dueDate"
                            placeholder="Pick a day"
                            type="date">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="message  ">
                    <el-button size="mini" style="margin-left: 0px;"
                               @click="addChat"> Add Chat
                    </el-button>
                    <template v-for="chat in mainForm.massage">
                        <el-row class="card" style="padding: 10px">
                            <el-col :span="8">
                                <div class="grid-content bg-purple">
                                    <el-input
                                        v-model="chat.description"
                                        clearable
                                        type="textarea">
                                    </el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="grid-content bg-purple-light">
                                    <el-select v-model="chat.contractDocument" placeholder="Select">
                                        <el-option
                                            v-for="item in ContractDoc"
                                            :key="item.name"
                                            :label="item.name"
                                            :value="item.description"
                                            size="mini">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="grid-content bg-purple">{{ chat.createdBy }}</div>
                            </el-col>
                        </el-row>


                    </template>
                </el-form-item>
                <el-button
                    size="mini"
                    title="Save"
                    type="primary"
                    @click="save"
                >Save
                </el-button>
                <el-button
                    size="mini"
                    title="publish"
                    type="primary"
                    @click="publish"
                >publish
                </el-button>
                <el-button
                    size="mini"
                    title="publish"
                    type="primary"
                    @click="index=0"
                >Close
                </el-button>
            </el-form>
        </div>
        <div v-if="index===2">
            <el-form
                ref="contractInfoForm"
                v-loading="loading"
                :label-position="formSettings.labelPosition"

                :size="formSettings.size"
                class="contract-info"
            >
                <el-row>

                    <el-col :span="6">
                        <div class="grid-content bg-purple-light">
                            <el-button
                                v-if="!mainForm.contractTask.contractDocument"
                                size="mini"
                                title="Save"
                                type="primary"
                                @click="save"
                            >Sent
                            </el-button>
                            <el-button v-if="!mainForm.contractTask.contractDocument" size="mini" style="margin-left: 0px;"
                                       @click="addChat"> Add New
                            </el-button>
                            <el-button v-if="!mainForm.contractTask.contractDocument" :disabled="AccRole" size="mini" style="margin-left: 0px;"
                                       @click="Accept"> Accept
                            </el-button>
                            <el-button size="mini" style="margin-left: 0px;"
                                       @click="index=0"> Close
                            </el-button>
                            <el-form-item label="">
                                <template v-for="chat in mainForm.massage">
                                    <el-row class="card" style="padding: 10px">
                                        <el-col :span="10">
                                            <div class="grid-content bg-purple">
                                                {{ chat.createdBy }}
                                                {{ chat.description }}
                                            </div>
                                        </el-col>
                                        <el-col :span="11">
                                            <div class="grid-content bg-purple-light">
                                                {{ chat.createdDate | formatDate }}
                                            </div>
                                        </el-col>
                                        <el-col :span="7">
                                            <div class="grid-content bg-purple">
                                                <el-button icon="el-icon-edit" size="mini" style="margin-left: 0px;"
                                                           type="primary" @click="EditContract(chat)">
                                                </el-button>
                                                <el-button icon="el-icon-view" size="mini" style="margin-left: 0px;"
                                                           type="primary"
                                                           @click="ViewContract(chat)">
                                                </el-button>
                                            </div>
                                        </el-col>

                                    </el-row>
                                </template>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col :span="18">
                        <div class="card">
                            <el-form-item label="">
                                <el-row style="padding: 10px">
                                    <el-col :span="8">
                                        <div class="grid-content bg-purple">
                                            <el-input
                                                v-model="mainForm.massageLast.description"
                                                :disabled="mainForm.massageLast.id"
                                                clearable
                                            >
                                            </el-input>
                                        </div>
                                    </el-col>
                                    <el-col :span="8">
                                        <div class="grid-content bg-purple-light">
                                            <el-select v-if="!isVendor" v-model="mainForm.massageLast.contractDocument"
                                                       :disabled="mainForm.massageLast.id"
                                                       placeholder="Select"
                                            >
                                                <el-option
                                                    v-for="item in ContractDoc"
                                                    :key="item.name"
                                                    :label="item.name"
                                                    :value="item.description"
                                                    size="mini">
                                                </el-option>
                                            </el-select>
                                        </div>
                                    </el-col>
                                    <el-col :span="8">
                                        <div class="grid-content bg-purple">{{ mainForm.massageLast.createdBy }}
                                        </div>
                                    </el-col>
                                </el-row>

                            </el-form-item>
                            <el-form-item>
                                <html-editor v-model="mainForm.massageLast.contractDocument"
                                             :disabled="getdisable(mainForm.massageLast.id)"
                                             size="mini"></html-editor>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
            </el-form>
        </div>
    </div>
</template>
<script lang="ts" src="./task.component.ts"></script>
<style lang="scss">


</style>
