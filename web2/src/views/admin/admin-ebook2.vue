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
                     :data-source="ebooks" :pagination="pagination"
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
            title="电子书表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <p>test</p>
        <a-form :model="ebook" :label-col="{span:6}" >
            <a-form-item label="封面">
                <a-input v-model:value="ebook.cover" />
            </a-form-item>
            <a-form-item label="名称">
                <a-input v-model:value="ebook.name" />
            </a-form-item>
            <a-form-item label="分类一">
                <a-input v-model:value="ebook.category1Id" />
            </a-form-item>
            <a-form-item label="分类二">
                <a-input v-model:value="ebook.category2Id" />
            </a-form-item>
            <a-form-item label="描述">
                <a-input v-model:value="ebook.description" />
            </a-form-item>
        </a-form>
    </a-modal>

</template>


<script lang="ts">
    import axios from "axios";
    import { defineComponent ,onMounted, ref} from 'vue';
    import {message} from 'ant-design-vue'

    export default defineComponent({
        name:'AdminEbook',
        setup() {
            const param = ref();
            param.value={};
            const ebooks = ref();
            const pagination = ref({
                current: 1,
                pageSize: 8,
                total: 0
            });
            const loading = ref(false);
            const columns = [{
                title: '封面',
                dataIndex: 'cover',
                slots:{customRender: 'cover'}
            },
                {
                    title: '名字',
                    dataIndex: 'name',
                },
                {
                    title: '分类一',
                    key: 'category1Id',
                    dataIndex: 'category1Id'
                },
                {
                    title: '分类二',
                    key: 'category2Id',
                    dataIndex: 'category2Id'
                },
                {
                    title: '文档数',
                    dataIndex: 'docCount'
                },
                {
                    title: '阅读数',
                    dataIndex: 'viewCount'
                },
                {
                    title: '点赞数',
                    dataIndex: 'voteCount'
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
                axios.get("/ebook/list",{
                    params:{
                        page:params.page,
                        size: params.size
                    }
                }).then((response)=>{
                    loading.value = false;
                    const data = response.data;
                    if(data.success) {
                        ebooks.value = data.content.list;
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
            const ebook = ref({});
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleModalOk = ()=>{
                modalLoading.value = true;
                axios.post("/ebook/save",ebook.value).then((response)=>{
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
                ebook.value = record;
            };

            /**
             * 新增
             * **/
            const add = ()=>{
                modalVisible.value = true;
                ebook.value = {};
            };
            /**
             * 删除
             * **/
            const handleDelete = (id: number)=>{
                axios.delete("/ebook/delete"+id).then((response)=>{
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
                ebooks,
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
                ebook
            }
        }
    });
</script>


<style scoped>
    img{
        width:50px;
    }
</style>