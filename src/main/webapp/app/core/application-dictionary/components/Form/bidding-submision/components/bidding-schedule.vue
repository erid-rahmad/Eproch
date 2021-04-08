<template>
    <div class="app-container">

        <el-divider content-position="left">
            <h4>Project Information</h4>
        </el-divider>
        <el-row :gutter="24">
            <el-col :span="20">
                <el-table v-loading="processing" ref="biddingSchedule" highlight-current-row border stripe size="mini" style="width: 100%; height: 100%" :data="projectinformation">

                    <el-table-column min-width="30" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="100" prop="name" label="Information" />
                    <el-table-column min-width="100" prop="attachmentName" label="atachment">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-download" size="mini" type="primary" @click="downloadAttachment(row)">
                                {{ row.attachmentName }}
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-divider content-position="left">
            <h4>Vendor Qualification</h4>
        </el-divider>
        <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
            <el-row :gutter="24">
                <el-col :span="18">
                    <el-form-item label="Prequalification Method" prop="title">
                        <el-dropdown>
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
                        </el-dropdown>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Kriteria" prop="documentNo">
                        <h7>Pengelola K3L</h7>
                    </el-form-item>
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <h7>Faktor utama</h7>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                Safety_pilicy.pdf
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
                        <h7>Faktor Pendukung</h7>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                enviromentalpolicy.pdf
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
                    <el-form-item label="Kriteria" prop="vurrency">
                        <h7>Organisasi dan Management</h7>
                    </el-form-item>
                    <el-form-item label="Sub Kriteria" prop="vurrency">
                        <h7>Organisasi dan Management</h7>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Attachment" prop="bidingType">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-upload" size="mini" type="primary" @click="downloadAttachment(row)">
                                sop dan struktur organisasi.pdf
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
        <el-row :gutter="columnSpacing">
            <el-col :span="24">
                <el-button type="primary" size="mini" style="margin-left: 0px;position: absolute; top: 50%; left: 50%;" v-loading.fullscreen.lock="fullscreenLoading" @click="validate">
                    Submit
                </el-button>
            </el-col>
        </el-row>

    </div>
</template>
<script lang="ts" src="./bidding-schedule.component.ts"></script>
