<template>
    <div class="app-container">
        <el-row :gutter="columnSpacing">
            <el-col :span="24">
                <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="dialogTableVisible11 = true">
                    Submit <em class="el-icon-arrow-right"></em>
                </el-button>
            </el-col>
        </el-row>

        <el-divider content-position="left">
            <h4>Technical Proposal</h4>
        </el-divider>
        <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
            <el-row :gutter="24">
                <el-col :span="18">
                    <el-form-item label="Prequalification Method" prop="title">
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Kriteria" prop="documentNo">
                        <h7>Kualifikasi Personil</h7>
                    </el-form-item>
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <h7>Team Leader</h7>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                TeamleaderResume.pdf
                            </el-button>
                        </template>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-row>
            <el-col :span="24">
                <el-table v-loading="processing" ref="projectInformation" highlight-current-row border stripe size="mini" style="height: 100%" :data="dummy">
                    <el-table-column min-width="10" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="120" prop="dockument" label="Question" />
                    <el-table-column min-width="30" prop="dockument1" label="requirment" />
                    <el-table-column min-width="150" label="Answer">
                        <template slot-scope="{row}">
                            <el-input class="form-input" size="mini" v-model="row.input" clearable required></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column min-width="150" label="Dockument">
                        <template slot-scope="{row}">
                            <el-checkbox v-model="row.checked">Checked</el-checkbox>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <h1></h1>
        <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
            <el-row :gutter="24">
                <el-col :span="12">
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <h7>Detail Pelaksanaan Pekerjaan</h7>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                Project.pdf
                            </el-button>
                        </template>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-row>
            <el-col :span="24">
                <el-table v-loading="processing" ref="projectInformation" highlight-current-row border stripe size="mini" style="height: 100%" :data="dummy1">
                    <el-table-column min-width="10" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="120" prop="dockument" label="Question" />
                    <el-table-column min-width="30" prop="dockument1" label="requirment" />
                    <el-table-column min-width="150" prop="vendorsubmision" label="Answer">
                        <template slot="header">
                        </template>
                        <template slot-scope="{row}">
                            <el-input class="form-input" size="mini" v-model="row.dockument3" clearable required></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column min-width="150" label="Dockument">
                        <template slot-scope="{row}">
                           <el-checkbox v-model="row.checked">Checked</el-checkbox>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <h1></h1>
        <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
            <el-row :gutter="24">
                <el-col :span="12">
                    <!-- <el-form-item label="Kriteria" prop="vurrency">
                        <h7>Organisasi dan Management</h7>
                    </el-form-item> -->
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <h7>Tenaga Ahli</h7>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                Team_resume.pdf
                            </el-button>
                        </template>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-row>
            <el-col :span="24">
                <el-table v-loading="processing" ref="projectInformation" highlight-current-row border stripe size="mini" style="height: 100%" :data="dummy2">
                    <el-table-column min-width="10" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="120" prop="dockument" label="Question" />
                    <el-table-column min-width="30" prop="dockument1" label="requirment" />
                    <el-table-column min-width="150" prop="vendorsubmision" label="Answer">
                        <template slot="header">
                        </template>
                        <template slot-scope="{row}">
                            <el-input class="form-input" size="mini" v-model="row.dockument3" required></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column min-width="150" label="Dockument">
                        <template slot-scope="{row}">
                            <el-checkbox v-model="row.checked">Checked</el-checkbox>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <h1></h1>
        <!-- <el-row :gutter="columnSpacing">
            <el-col :span="24">
                <el-button type="primary" size="mini" style="margin-left: 0px;position: absolute; top: 50%; left: 50%;" v-loading.fullscreen.lock="fullscreenLoading" @click="validate">
                    Submit
                </el-button>
            </el-col>
        </el-row> -->

    </div>
</template>
<script lang="ts" src="./technical-proposal.component.ts"></script>
