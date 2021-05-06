<template>
    <div class="app-container">
        <el-divider content-position="left">
            <h4>Technical Proposal</h4>
        </el-divider>
        <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
            <el-row :gutter="24">
                <el-col :span="18">
                    <el-form-item label="Prequalification Method" prop="title">
                        <!-- <el-dropdown>
                            <el-button type="primary">
                                Metode Prekualifikasi 2021<i class="el-icon-arrow-down el-icon--right"></i>
                            </el-button>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>Action 1</el-dropdown-item>
                                <el-dropdown-item>Action 2</el-dropdown-item>
                                <el-dropdown-item>Action 3</el-dropdown-item>
                                <el-dropdown-item>Action 4</el-dropdown-item>
                                <el-dropdown-item>Action 5</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown> -->
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Kriteria" prop="documentNo">
                        <span>Kualifikasi Personil</span>
                    </el-form-item>
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <span>Team Leader</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                TeamLeaderResume.pdf
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
                    <el-table-column min-width="150"  label="Answer">                 
                        <template slot-scope="{row}">
                            <el-input class="form-input" size="mini" v-model="row.input" clearable required></el-input>
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
                        <span>Detail Pelaksanaan Pekerjaan</span>
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
                        </template></el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <h1></h1>
        <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
            <el-row :gutter="24">
                <el-col :span="12">
                    <!-- <el-form-item label="Kriteria" prop="vurrency">
                        <span>Organisasi dan Management</span>
                    </el-form-item> -->
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <span>Tenaga Ahli</span>
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
                        </template></el-table-column>
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
