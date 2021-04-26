<template>
    <div class="details-announcement">
       <el-row>
                <el-row :gutter="columnSpacing">
                    <el-col :span="24">
                        <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                            Back
                        </el-button>
                    </el-col>
                </el-row>
                <el-col :span="24">
                    <el-divider content-position="left">
                        <h4>Pendaftaran dan Penyampaian Dokumen Pengadaan</h4>
                    </el-divider>
                    <el-table :data="moreinfo1" style="width: 80%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="80" sortable prop="JudulPengadaan" label="Judul Pengadaan" />
                        <el-table-column align="center" min-width="40" label="Action">
                            <template slot-scope="row">
                                <el-button size="mini" icon="el-icon-view" type="primary" @click="moreinfo()" />
                                <el-button size="mini" icon="el-icon-view" type="primary" @click="moreinfo()" />
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-divider content-position="left">
                        <h4>Penyedia yang Sudah Mendaftar</h4>
                    </el-divider>
                    <el-table :data="moreinfo2" style="width: 80%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="80" sortable prop="namaperusahaan" label="Nama Perusahaan" />
                        <el-table-column min-width="100" sortable prop="tanggaldaftar" label="Tanggal Daftar" />
                    </el-table>
                    <el-divider content-position="left">
                        <h4>Penyedia yang Menolak</h4>
                    </el-divider>
                    <el-table :data="moreinfo3" style="width: 80%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="80" sortable prop="namaperusahaan" label="Nama Perusahaan" />
                        <el-table-column min-width="100" sortable prop="tanggaldaftar" label="Tanggal Tolak" />
                        <el-table-column min-width="100" sortable prop="alasan" label="Alasan" />
                    </el-table>
                    <el-divider content-position="left">
                        <h4>Penyedia yang Tidak Menjawab</h4>
                    </el-divider>
                    <el-table :data="moreinfo4" style="width: 80%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="80" sortable prop="namaperusahaan" label="Nama Perusahaan" />


                    </el-table>
                </el-col>

            </el-row>

    </div>
</template>
<script lang="ts" src="./details-announcement.component.ts"></script>
<style lang="scss">
    .compact .details-announcement {
        .el-table--mini {

            td,
            th {
                height: 35px;
            }
        }
    }

</style>
<style lang="scss" scoped>
    .details-announcement {
        .criteria-section {

            &:not(.criteria-0),
            >.el-col>.sub-criteria-section:not(.sub-0) {
                margin-top: 10px;
            }
        }
    }

</style>
