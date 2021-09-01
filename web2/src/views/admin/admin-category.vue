<template>
    <a-layout>
      <a-layout-content
              :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <div class="about">
            <p>
                <a-button type="primary" @click="add()" >新增</a-button>
            </p>
            <a-table :columns="columns" :row-key="record=> record.id"
                     :data-source="categorys" :pagination="pagination"
                     :loading="loading" @change="handleTableChange">
                <template #cover="{ text:cover }">
                    <img v-if="cover" :src="cover" alt="avatar">
                </template>
                <template v-slot:action="{text,record}">
                    <a-space size="small">
                        <a-button type="primary" @click="edit(record)">编辑</a-button>
                        <a-popconfirm
                                title="删除后不可恢复，确认删除？"
                                ok-text="是"
                                cancel-text="否"
                                @confirm="handleDelete(record.id)"
                        >
                            <a-button type="danger" >删除</a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </a-table>
        </div>
      </a-layout-content>
    </a-layout>
    <a-modal
            title="分类表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <p>test</p>
        <a-form :model="category" :label-col="{span:6}" >
            <a-form-item label="名称">
                <a-input v-model:value="category.name" />
            </a-form-item>
            <a-form-item label="父分类">
                <a-input v-model:value="category.parent" />
            </a-form-item>
            <a-form-item label="顺序">
                <a-input v-model:value="category.sort" />
            </a-form-item>
        </a-form>
    </a-modal>

</template>


<script lang="ts">
    import axios from "axios";
    import { defineComponent ,onMounted, ref} from 'vue';
    import {message} from 'ant-design-vue'
    import {Tool} from '@/util/tool'

    export default defineComponent({
        name:'AdminCategory',
        setup() {
            const param = ref();
            param.value={};
            const categorys = ref();
            const pagination = ref({
                current: 1,
                pageSize: 8,
                total: 0
            });
            const loading = ref(false);
            const columns = [
                {
                    title: '名字',
                    dataIndex: 'name',
                },
                {
                    title: '父分类',
                    key: 'parent',
                    dataIndex: 'parent'
                },
                {
                    title: '顺序',
                    dataIndex: 'sort'
                },
                {
                    title: '操作',
                    key: 'action',
                    slots:{customRender: 'action'}
                }
            ];
            /**
             * 数据查询
             * **/
            const handleQuery = (params: any)=>{
                loading.value = true;
                axios.get("/category/list",{
                    params:{
                        page:params.page,
                        size: params.size
                    }
                }).then((response)=>{
                    loading.value = false;
                    const data = response.data;
                    if(data.success) {
                        categorys.value = data.content.list;
                        //重置分页
                        pagination.value.current = params.page;
                        pagination.value.total = data.content.total;
                    }else{
                        message.error(data.message);
                    }
                });
            };
            /**
             * 表格点击
             * **/
            const handleTableChange = (pagination: any)=>{
                console.log("查看分页参数"+pagination);
                handleQuery({
                   page: pagination.current,
                   size: pagination.pageSize
                });
            };

            /**
             * 表单
             * **/
            const category = ref({});
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleModalOk = ()=>{
                modalLoading.value = true;
                axios.post("/category/save",category.value).then((response)=>{
                    const data = response.data;
                    modalLoading.value = false;
                    if(data.success){
                        modalVisible.value = false;
                        //重新加载数据list
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize
                        });
                    }else{
                        message.error(data.message);
                    }
                });
            };


            /**
             * 编辑
             * **/
            const edit = (record: any)=>{
                modalVisible.value = true;
                category.value = Tool.copy(record);
            };

            /**
             * 新增
             * **/
            const add = ()=>{
                modalVisible.value = true;
                category.value = {};
            };
            /**
             * 删除
             * **/
            const handleDelete = (id: number)=>{
                axios.delete("/category/delete"+id).then((response)=>{
                    const data = response.data;//data=commonResp
                    if(data.success){
                        //重新加载数据list
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize
                        });
                    }
                });
            };

            onMounted(()=>{
                handleQuery({
                    page: 1,
                    size: pagination.value.pageSize
                });
            });



            //返回数据去页面
            return{
                categorys,
                pagination,
                columns,
                loading,
                handleTableChange,
                handleQuery,

                edit,
                handleDelete,
                add,

                modalVisible,
                modalLoading,
                handleModalOk,
                category
            }
        }
    });
</script>


<style scoped>
    img{
        width:50px;
    }
</style>