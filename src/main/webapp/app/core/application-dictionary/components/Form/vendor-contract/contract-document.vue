<template>
    <div class="contract-document">
        <el-table
            ref="documents"
            v-loading="loading"
            :data="documents"
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

            <!--      <el-table-column label="File" min-width="200">-->
            <!--        <template v-slot="{ row }">-->
            <!--          <el-button-->
            <!--            class="btn-attachment"-->
            <!--            icon="el-icon-download"-->
            <!--            size="mini"-->
            <!--            :title="row.attachmentName"-->
            <!--            type="primary"-->
            <!--            @click="downloadAttachment(row)"-->
            <!--          >-->
            <!--            {{ row.attachmentName }}-->
            <!--          </el-button>-->
            <!--        </template>-->
            <!--      </el-table-column>-->

            <el-table-column align="center" fixed="right" width="100">
                <template slot="header">
                    <el-button icon="el-icon-plus" size="mini" type="primary"
                               @click="addNew"></el-button>
                </template>
                <template v-slot="{ row }">
                    <el-button v-if="!readOnly" icon="el-icon-delete" size="mini" type="danger"
                               @click="removeDocument(row)"></el-button>
                    <el-button v-if="!readOnly" size="mini" type="danger" @click="view(row)">View</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog
            :show-close="false"
            :visible.sync="documentFormVisible"
            title="Upload Document"
            width="60%"
        >
            <template>
                <el-form
                    ref="documentForm"
                >
                    <el-form-item label="Title" prop="name">
                        <el-input v-model="document.name" class="form-input" clearable placeholder="Title"></el-input>
                    </el-form-item>

                    <el-divider content-position="left">
                        <h4>Header</h4>
                    </el-divider>
                    <el-row>
                        <el-col :span="4">Introduction
                            <div class="grid-content bg-purple"></div>
                        </el-col>
                        <el-col :span="4">
                            <template>
                                <el-select v-model="document.header" placeholder="Select">
                                    <el-option
                                        v-for="item in headerOption"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        size="mini">
                                    </el-option>
                                </el-select>
                            </template>
                            <div class="grid-content bg-purple-light"></div>
                        </el-col>
                        <el-col :span="16">
                            <el-input
                                v-model="document.header"
                                :rows="2"
                                placeholder="Please input"
                                type="textarea">
                            </el-input>
                            <div class="grid-content bg-purple"></div>
                        </el-col>
                    </el-row>
                    <el-divider content-position="left">
                        <h4>Body</h4>
                    </el-divider>
                    <el-row>
                        <el-col :span="4">Introduction
                            <div class="grid-content bg-purple"></div>
                        </el-col>
                        <el-col :span="4">
                            <template>
                                <el-select v-model="document.body" placeholder="Select">
                                    <el-option
                                        v-for="item in bodyOption"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        size="mini">
                                    </el-option>
                                </el-select>
                            </template>
                            <div class="grid-content bg-purple-light"></div>
                        </el-col>
                        <el-col :span="16">
                            <el-input
                                v-model="document.body"
                                :rows="2"
                                placeholder="Please input"
                                type="textarea">
                            </el-input>
                            <div class="grid-content bg-purple"></div>
                        </el-col>
                    </el-row>
                    <el-divider content-position="left">
                        <h4>Footer</h4>
                    </el-divider>
                    <el-row>
                        <el-col :span="4">Introduction
                            <div class="grid-content bg-purple"></div>
                        </el-col>
                        <el-col :span="4">
                            <template>
                                <el-select v-model="document.footer" placeholder="Select">
                                    <el-option
                                        v-for="item in footerOption"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        size="mini">
                                    </el-option>
                                </el-select>
                            </template>
                            <div class="grid-content bg-purple-light"></div>
                        </el-col>
                        <el-col :span="16">
                            <el-input
                                v-model="document.footer"
                                :rows="2"
                                placeholder="Please input"
                                type="textarea">
                            </el-input>
                            <div class="grid-content bg-purple"></div>
                        </el-col>
                    </el-row>
                </el-form>

                <div slot="footer">
                    <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                               @click="documentFormVisible = false">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                    <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary"
                               @click="saveDocument">
                        {{ $t('entity.action.save') }}
                    </el-button>
                </div>
            </template>
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
</template>
<script lang="ts" src="./contract-document.component.ts"></script>
<style lang="scss">
.contract-document {
    .btn-attachment {
        width: 100%;
    }
}

</style>
