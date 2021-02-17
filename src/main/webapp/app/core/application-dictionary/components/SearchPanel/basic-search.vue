<template>
    <div class="basic-search">

        <el-form 
          label-width="100px"
          :model="row"
          size="mini"
        >

            <el-form-item 
              v-for="field in listFields"
              :key="field.id"
              :prop="fieldName(field)"
              :label="field.name">
                
                <el-select
                  v-if="isBooleanField(field)"
                  v-model="row[fieldName(field)]"
                  size="small"
                  filterable
                >
                  <el-option
                    v-for="item in booleanOption"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
                
                <el-select
                  v-else-if="isTableDirectLink(field)"
                  v-model="row[fieldName(field)]"
                  :remote="true"
                  :remote-method="fetchTableDirectData"
                  size="small"
                  filterable
                  @focus="setTableDirectReference(field)"
                >
                  <el-option
                    v-for="item in getTableDirectReferences(field)"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>

                <el-select
                  v-else-if="hasReferenceList(field)"
                  v-model="row[fieldName(field)]"
                  size="small"
                  filterable
                >
                  <el-option
                    v-for="item in getReferenceList(field)"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  />
                </el-select>

                <el-input
                  v-else-if="isStringField(field)"
                  v-model="row[fieldName(field)]"
                  size="small"
                  clearable
                />

                <el-input-number
                  v-else-if="isNumericField(field)"
                  v-model="row[fieldName(field)]"
                  controls-position="right"
                  size="small"
                  :min="getMinValue(field)"
                  :max="getMaxValue(field)"
                />

            </el-form-item>
            <el-form-item>
                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-search"
                    @click="filterBasic"
                    title="Execute">
                    Execute
                </el-button>

                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="warning" 
                    icon="el-icon-refresh"
                    @click="clear"
                    plain
                    title="Clear">
                    Clear
                </el-button>

                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="default" 
                    icon="el-icon-close"
                    @click="close"/>
                
            </el-form-item>

        </el-form>
    </div>
</template>
<script lang="ts" src="./basic-search.component.ts"></script>
