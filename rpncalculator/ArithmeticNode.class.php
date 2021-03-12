<?php
  class ArithmeticNode {

    private $value;
    private $firstOperand;
    private $secondOperand;

    function __construct(&$inputArray, $functionTable) {
      $frontChar = array_shift($inputArray);
      // not an operator
      if (!array_key_exists($frontChar, $functionTable)) {
        $this->value = $frontChar;
        $this->firstOperand = null;
        $this->secondOperand = null;
      } else {
        $this->value = $functionTable[$frontChar];
        $this->firstOperand = new ArithmeticNode($inputArray, $functionTable);
        $this->secondOperand = new ArithmeticNode($inputArray, $functionTable);
      }
    }

    public function getValue() {
      // if leaf
      if (is_null($this->firstOperand) && is_null($this->secondOperand)) {
        return $this->value;
      } else {
        switch ($this->value) {
          case 'add':
            return $this->add($this->firstOperand->getValue(), $this->secondOperand->getValue());
            break;
          case 'sub':
            return $this->sub($this->firstOperand->getValue(), $this->secondOperand->getValue());
            break;
          case 'mult':
            return $this->mult($this->firstOperand->getValue(), $this->secondOperand->getValue());
            break;
          case 'div':
            return $this->div($this->firstOperand->getValue(), $this->secondOperand->getValue());
            break;
        }
      }
    }

    private function add($v1, $v2) {
      return $v1 + $v2;
    }

    private function sub($v1, $v2) {
      return $v1 - $v2;
    }

    private function mult($v1, $v2) {
      return $v1 * $v2;
    }

    private function div($v1, $v2) {
      return $v1 / $v2;
    }

  }
?>
