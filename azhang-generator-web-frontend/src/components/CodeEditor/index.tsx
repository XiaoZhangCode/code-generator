import ace from 'ace-builds'; // 引入ace的库(脚本),里面有一些方法
import 'ace-builds/src-noconflict/mode-java';
import 'ace-builds/src-noconflict/theme-chrome'; // 使用GitHub主题
import React, { useEffect, useState } from 'react';
import AceEditor from 'react-ace';

interface CodeEditorProps {
  // 添加一个回调函数类型来处理编辑器内容变化
  onJsxChange: (newJsxValue: string) => void;
  defaultValue?: any;
}

const CodeEditor: React.FC<CodeEditorProps> = ({ onJsxChange, defaultValue }) => {
  const [jsx, setJsx] = useState<any>();

  useEffect(() => {
    // 手动加载worker脚本
    ace.config.set('workerPath', 'https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.12/');
    ace.config.set('modePath', 'https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.12/');
    ace.config.set('themePath', 'https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.12/');
  }, []);

  const handleEditorChange = (value: string) => {
    setJsx(value);
    onJsxChange(value); // 调用外部传入的回调函数，将新的 jsx 值传递出去
  };

  return (
    <AceEditor
      mode="java" // 设置编辑器的语言为JSON
      theme="chrome" // 设置编辑器主题
      name="CodeEditor"
      fontSize={16}
      height="500px"
      style={{ marginBottom: 24, minWidth: 800 }}
      width="100%"
      showGutter
      onBlur={() => {
        const editor = ace.edit('CodeEditor');
        setJsx(editor.getValue());
      }}
      value={jsx}
      defaultValue={defaultValue ? JSON.stringify(defaultValue, null, 2) : ''}
      wrapEnabled //自动换行
      highlightActiveLine //突出活动线
      enableSnippets //启用代码段
      setOptions={{
        enableBasicAutocompletion: true,
        enableLiveAutocompletion: true,
        enableSnippets: true,
        showLineNumbers: true,
        tabSize: 2,
      }}
      onChange={handleEditorChange}
    />
  );
};

export default CodeEditor;
