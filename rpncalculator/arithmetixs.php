<?php
  include_once("ArithmeticNode.class.php");

  $input = "- * / 15 - 7 + 1 1 3 + 2 + 1 1";
  $functionTable = array('+' => 'add', '-' => 'sub', '*' => 'mult', '/' => 'div');

  print_r("evalStr:".evalStr($input, $functionTable)."\n");
  $processed = explode(" ", $input);
  $evalNode = new ArithmeticNode($processed, $functionTable);
  print_r("ArithmeticNode:".$evalNode->getValue());

  function evalStr($inputStr, $ftable) {
    $input = $inputStr;
    $preprocessed = explode(" ", $input);
    return process($preprocessed, $ftable);
  }

  function process($pprocessed, $ftable) {
    $operatorStack = array(); // + - * /
    $operandStack = array(); // Numbers
    $pendingOperand = false;
    foreach ($pprocessed as $symbol) {
      if (array_key_exists($symbol, $ftable)) {
        array_unshift($operatorStack, $symbol);
        $pendingOperand = false;
      } else {
        $operand = $symbol;
        if ($pendingOperand === true) {
          while (!empty($operandStack)) {
            $operand_1 = array_shift($operandStack);
            // maps key -> value in ftable
            $operator = $ftable[array_shift($operatorStack)];
            switch ($operator) {
              case 'add':
                $operand = add($operand_1, $operand);
                break;
              case 'sub':
                $operand = sub($operand_1, $operand);
                break;
              case 'mult':
                $operand = mult($operand_1, $operand);
                break;
              case 'div':
                $operand = div($operand_1, $operand);
                break;
            }
          }
        }
        array_unshift($operandStack, $operand);
        $pendingOperand = true;
      }
    }
    return array_shift($operandStack);
  }

  function add($v1, $v2) {
    return $v1 + $v2;
  }

  function sub($v1, $v2) {
    return $v1 - $v2;
  }

  function mult($v1, $v2) {
    return $v1 * $v2;
  }

  function div($v1, $v2) {
    return $v1 / $v2;
  }

?>
