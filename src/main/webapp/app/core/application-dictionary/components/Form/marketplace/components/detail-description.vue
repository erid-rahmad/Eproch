<template>
    <div class="app-container">

        <h4><b>{{ headerDescription }}</b></h4>

        <div v-if="valueDescription == 'desc'">
            <ul>
                <li v-for="desc in descriptionDetail" :key="desc.key">
                    {{ desc.value }}
                </li>
            </ul>
        </div>

        <div v-if="valueDescription == 'excl'">
            <el-row>
                <el-col :span="24">
                    <b>Proyektor BenQ untuk Ruang Meeting Besar</b>
                </el-col>

            </el-row>

            <el-row>
                <el-col :span="24">
                    BenQ MW612 adalah proyektor dengan kecerahan 4000 lumens. Tingkat kecerahan tinggi membuat Anda bisa melakukan presentasi di ruang meeting kecil dan cukup terang. Dengan tingkat luminasi sebesar itu, proyektor ini ideal untuk digunakan di ruang meeting dengan kurang lebih 20 peserta. Proyektor ini bisa menghasilkan warna natural dengan kontras terbaik sehingga nyaman dibaca. Resolusi BenQ MW612 mencapai WXGA. Berikut ini semua kelebihan proyektor BenQ MW612.
                </el-col>
            </el-row>

        </div>

        <div v-if="valueDescription == 'spec'">

            <el-row v-for="spec in descriptionSpec" :key="spec.key">
                <el-col :span="4">
                    {{ spec.name }}
                </el-col>
                <el-col :span="20">
                    {{ spec.value }}
                </el-col>
            </el-row>

        </div>

    </div>
</template>

<script lang="ts" src="./detail-description.component.ts">
</script>

<style lang="scss" scoped>
    .el-row {
        margin-bottom: 10px;
    }
</style>
